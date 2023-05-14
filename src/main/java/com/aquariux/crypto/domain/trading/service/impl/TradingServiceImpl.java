package com.aquariux.crypto.domain.trading.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aquariux.crypto.domain.aggregation.PriceAggregation;
import com.aquariux.crypto.domain.aggregation.service.PriceAggregationService;
import com.aquariux.crypto.domain.trading.TradingHistory;
import com.aquariux.crypto.domain.trading.TradingRequest;
import com.aquariux.crypto.domain.trading.TradingResponse;
import com.aquariux.crypto.domain.trading.service.TradingHistoryService;
import com.aquariux.crypto.domain.trading.service.TradingService;
import com.aquariux.crypto.domain.wallet.Wallet;
import com.aquariux.crypto.domain.wallet.service.WalletService;
import com.aquariux.crypto.infrastructure.adapter.trading.TradingProvider;
import com.aquariux.crypto.infrastructure.adapter.trading.TradingProviderFactory;
import com.aquariux.crypto.infrastructure.enums.SupportedTradingPair;
import com.aquariux.crypto.infrastructure.enums.TradingResultCode;
import com.aquariux.crypto.infrastructure.enums.TradingStatus;
import com.aquariux.crypto.infrastructure.enums.TradingType;

@Service
@Transactional
public class TradingServiceImpl implements TradingService {

    @Autowired
    private WalletService walletService;

    @Autowired
    private PriceAggregationService priceAggregationService;
    
    @Autowired
    private TradingHistoryService tradingHistoryService;

    @Override
    public TradingResponse trade(String userId, TradingRequest tradingRequest) {
        Wallet currentWallet = walletService.findByUserId(userId);
        BigDecimal usdt = currentWallet.getBtc(); // backup the balance
        BigDecimal eth = currentWallet.getEth(); // backup the balance
        BigDecimal btc = currentWallet.getBtc(); // backup the balance

        PriceAggregation priceAggregation = priceAggregationService.findByTradingPair(tradingRequest.getTradingPair().toUpperCase());
        TradingHistory tradingHistory = new TradingHistory(tradingRequest);
        tradingHistory.setTradingStatus(TradingStatus.PENDING.status());
        tradingHistory.setCreateUser(userId);

        // validate
        TradingResponse tradingResponse = validateTransaction(tradingRequest, currentWallet, priceAggregation);
        if (tradingResponse.getResultCode() != TradingResultCode.SUCCESS.code()) {
            return tradingResponse;
        }

        // doTransaction
        // update wallet and Transaction -> send trading request -> update history -> if request is failed, re-update wallet
        updateWallet(userId, tradingHistory, tradingRequest, currentWallet, priceAggregation);
        doTransaction(tradingHistory);
        if (tradingHistory.getResultCode() != TradingStatus.DONE.status()) {
            // In case of failure, we have to restore the wallet state
            currentWallet.setUsdt(usdt);
            currentWallet.setEth(eth);
            currentWallet.setBtc(btc);
            walletService.save(currentWallet);
        }
        tradingResponse.setTradingId(tradingHistory.getTradingId());
        tradingResponse.setTradingTime(tradingHistory.getCreateDate());
        return tradingResponse;
    }

    private TradingResponse validateTransaction(TradingRequest tradingRequest, Wallet currentWallet, PriceAggregation priceAggregation) {
        TradingResponse tradingResponse = new TradingResponse();
        tradingResponse.setRequestId(tradingRequest.getRequestId());
        tradingResponse.setRequestTime(tradingRequest.getRequestTime());

        // If the price aggregation scheduler does not run, we don't have information to do this transaction
        if (priceAggregation == null) {
            tradingResponse.setResultCode(TradingResultCode.GENERIC_ERROR.code());
            tradingResponse.setResultMessage("System is not ready.");
            return tradingResponse;
        }

        // ensure the balance is enough for this transaction
        if (currentWallet == null) {
            tradingResponse.setResultCode(TradingResultCode.INSUFFICIENT_FUNDS.code());
            tradingResponse.setResultMessage("Wallet is not existed.");
            return tradingResponse;
        }

        // The user can not buy if USDT is 0
        if (tradingRequest.getTradingType() == TradingType.BUY.type()
                && currentWallet.getUsdt().compareTo(BigDecimal.ZERO) <= 0) {
            tradingResponse.setResultCode(TradingResultCode.INSUFFICIENT_FUNDS.code());
            tradingResponse.setResultMessage("USDT is not enough.");
            return tradingResponse;
        }

        // for BUY and SELL
        if (tradingRequest.getTradingType() == TradingType.BUY.type()) {
            BigDecimal pendingUSDT = tradingRequest.getAmount().multiply(priceAggregation.getAskPrice());
            if (pendingUSDT.compareTo(currentWallet.getUsdt()) > 0) {
                tradingResponse.setResultCode(TradingResultCode.INSUFFICIENT_FUNDS.code());
                tradingResponse.setResultMessage("USDT is not enough.");
                return tradingResponse;
            }

            // ok! USDT is enough to buy the crypto
        } else if (tradingRequest.getTradingType() == TradingType.SELL.type()) {
            if (tradingRequest.getTradingPair().equals(SupportedTradingPair.BTCUSDT.toString())
                    && tradingRequest.getAmount().compareTo(currentWallet.getBtc()) > 0) {
                tradingResponse.setResultCode(TradingResultCode.INSUFFICIENT_FUNDS.code());
                tradingResponse.setResultMessage("BTC is not enough.");
                return tradingResponse;
            } else if (tradingRequest.getTradingPair().equals(SupportedTradingPair.ETHUSDT.toString())
                    && tradingRequest.getAmount().compareTo(currentWallet.getEth()) > 0) {
                tradingResponse.setResultCode(TradingResultCode.INSUFFICIENT_FUNDS.code());
                tradingResponse.setResultMessage("ETH is not enough.");
                return tradingResponse;
            }

            // ok to sell the crypto
        } else {
            // Unsupported trading type.

            tradingResponse.setResultCode(TradingResultCode.GENERIC_ERROR.code());
            tradingResponse.setResultMessage("Unsupported trading type.");
            return tradingResponse;
        }

        tradingResponse.setResultCode(TradingResultCode.SUCCESS.code());
        tradingResponse.setResultMessage("OK");
        return tradingResponse;
    }

    // This funtion will do the trading with external third party systems
    private void doTransaction(TradingHistory tradingHistory) {
        
        TradingProviderFactory tradingProviderFactory = new TradingProviderFactory();
        TradingProvider tradingProvider = tradingProviderFactory.getTradingProvider(tradingHistory.getTradingSource());
        int resultCode = TradingResultCode.SUCCESS.code();
        String resultMsg = "OK";

        if (tradingProvider.buy(tradingHistory.getTradingPair().toUpperCase(), tradingHistory.getAmount())) {
            // the transaction is successful.
            tradingHistory.setTradingStatus(TradingStatus.DONE.status());
            resultCode = TradingResultCode.SUCCESS.code();
            resultMsg = "OK";
        } else {
            // the transaction is failed.
            tradingHistory.setTradingStatus(TradingStatus.FAILED.status());
            resultCode = TradingResultCode.GENERIC_ERROR.code();
            resultMsg = "Transaction failed";
        }
        tradingHistory.setResultCode(resultCode);
        tradingHistory.setResultMessage(resultMsg);
        tradingHistory.setUpdateDate(new Date());
        tradingHistory = tradingHistoryService.save(tradingHistory);
    }
    
    private void updateWallet(String userId, TradingHistory tradingHistory, TradingRequest tradingRequest, Wallet currentWallet, PriceAggregation priceAggregation) {
        tradingHistory.setAmount(tradingRequest.getAmount());

        // for BUY and SELL
        if (tradingRequest.getTradingType() == TradingType.BUY.type()) {
            BigDecimal pendingUSDT = tradingRequest.getAmount().multiply(priceAggregation.getAskPrice());
            currentWallet.setUsdt(currentWallet.getUsdt().subtract(pendingUSDT));

            tradingHistory.setTradingSource(priceAggregation.getAskSource());
            tradingHistory.setPrice(priceAggregation.getAskPrice());
            tradingHistory.setTotal(pendingUSDT);
        } else if (tradingRequest.getTradingType() == TradingType.SELL.type()) {
            BigDecimal pendingUSDT = tradingRequest.getAmount().multiply(priceAggregation.getBidPrice());
            currentWallet.setUsdt(currentWallet.getUsdt().add(pendingUSDT));

            tradingHistory.setTradingSource(priceAggregation.getBidSource());
            tradingHistory.setPrice(priceAggregation.getBidPrice());
            tradingHistory.setTotal(pendingUSDT);
        }

        if (tradingRequest.getTradingPair().equals(SupportedTradingPair.BTCUSDT.toString())) {
            if (tradingRequest.getTradingType() == TradingType.BUY.type()) {
                currentWallet.setBtc(currentWallet.getBtc().add(tradingRequest.getAmount()));
            } else if (tradingRequest.getTradingType() == TradingType.SELL.type()) {
                currentWallet.setBtc(currentWallet.getBtc().subtract(tradingRequest.getAmount()));
            }
        } else if (tradingRequest.getTradingPair().equals(SupportedTradingPair.ETHUSDT.toString())) {
            if (tradingRequest.getTradingType() == TradingType.BUY.type()) {
                currentWallet.setEth(currentWallet.getEth().add(tradingRequest.getAmount()));
            } else if (tradingRequest.getTradingType() == TradingType.SELL.type()) {
                currentWallet.setEth(currentWallet.getEth().subtract(tradingRequest.getAmount()));
            }
        }

        currentWallet.setUpdateDate(new Date());
        currentWallet.setUpdateUser(userId);
        currentWallet = walletService.save(currentWallet);

        tradingHistory.setUsdt(currentWallet.getUsdt());
        tradingHistory.setEth(currentWallet.getEth());
        tradingHistory.setBtc(currentWallet.getBtc());
        tradingHistory = tradingHistoryService.save(tradingHistory);
    }
}

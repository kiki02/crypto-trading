package com.aquariux.crypto.infrastructure.adapter.trading.binance;

import java.math.BigDecimal;

import com.aquariux.crypto.infrastructure.adapter.trading.TradingProvider;

public class BinanceServiceProvider implements TradingProvider {

    @Override
    public boolean buy(String tradingPair, BigDecimal amount) {
        return true;
    }

    @Override
    public boolean sell(String tradingPair, BigDecimal amount) {
        return true;
    }

    
}

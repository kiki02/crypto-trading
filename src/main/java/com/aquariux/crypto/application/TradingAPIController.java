package com.aquariux.crypto.application;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aquariux.crypto.domain.trading.TradingHistory;
import com.aquariux.crypto.domain.trading.TradingRequest;
import com.aquariux.crypto.domain.trading.TradingResponse;
import com.aquariux.crypto.domain.trading.service.TradingHistoryService;
import com.aquariux.crypto.domain.trading.service.TradingService;
import com.aquariux.crypto.infrastructure.enums.TradingResultCode;
import com.aquariux.crypto.infrastructure.utils.Constants;

@RestController
@RequestMapping("/api/trading")
public class TradingAPIController {

    @Autowired
    private TradingService tradingService;

    @Autowired
    private TradingHistoryService tradingHistoryService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Object> createTrading(@Valid @RequestBody TradingRequest tradingRequest) {
        // we assumed that the user has already authenticated and authorised to access the APIs, so we will have the user_id to do this
        try {
            TradingResponse tradingResponse = tradingService.trade(Constants.DEFAULT_USER_ID, tradingRequest);
            if (tradingResponse.getResultCode() == TradingResultCode.SUCCESS.code()) {
                return new ResponseEntity<>(tradingResponse, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(tradingResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TradingResponse tradingResponse = new TradingResponse();
            tradingResponse.setRequestId(tradingRequest.getRequestId());
            tradingResponse.setRequestTime(tradingRequest.getRequestTime());
            tradingResponse.setResultCode(TradingResultCode.GENERIC_ERROR.code());
            tradingResponse.setResultMessage("Bad request.");

            return new ResponseEntity<>(tradingResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseEntity<Object> getTradingHistory() {
        // we assumed that the user has already authenticated and authorised to access the APIs, so we will have the user_id to do this
        List<TradingHistory> histories = tradingHistoryService.findByCreateUser(Constants.DEFAULT_USER_ID);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    @RequestMapping(value = "/detail/{tradingId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getTradingHistoryDetail(@PathVariable("tradingId") String tradingId) {
        TradingHistory history = tradingHistoryService.findByTradingId(tradingId);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

}

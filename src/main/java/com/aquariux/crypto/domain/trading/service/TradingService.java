package com.aquariux.crypto.domain.trading.service;

import com.aquariux.crypto.domain.trading.TradingRequest;
import com.aquariux.crypto.domain.trading.TradingResponse;

public interface TradingService {

    TradingResponse trade(String userId, TradingRequest tradingRequest);
}

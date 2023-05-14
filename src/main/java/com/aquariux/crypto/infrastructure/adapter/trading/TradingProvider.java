package com.aquariux.crypto.infrastructure.adapter.trading;

import java.math.BigDecimal;

public interface TradingProvider {

    boolean buy(String tradingPair, BigDecimal amount);
    boolean sell(String tradingPair, BigDecimal amount);
}

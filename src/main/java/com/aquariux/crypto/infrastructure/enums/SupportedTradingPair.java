package com.aquariux.crypto.infrastructure.enums;

public enum SupportedTradingPair {
    ETHUSDT("ETHUSDT"),
    BTCUSDT("BTCUSDT")
    ;

    private final String symbol;

    SupportedTradingPair(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}

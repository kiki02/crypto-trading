package com.aquariux.crypto.infrastructure.enums;

public enum TradingType {
    SELL(0), 
    BUY(1)
    ;

    private int type;

    TradingType(int type) {
        this.type = type;
    }

    public int type() {
        return type;
    }
}

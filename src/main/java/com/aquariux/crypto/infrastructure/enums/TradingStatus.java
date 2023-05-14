package com.aquariux.crypto.infrastructure.enums;

public enum TradingStatus {
    DONE(0), 
    PENDING(1),
    FAILED(2),
    ;

    private int status;

    TradingStatus(int status) {
        this.status = status;
    }

    public int status() {
        return status;
    }
}

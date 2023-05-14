package com.aquariux.crypto.infrastructure.enums;

public enum TradingResultCode {
    SUCCESS(0), 
    INSUFFICIENT_FUNDS(1),
    GENERIC_ERROR(9)
    ;

    private int code;

    TradingResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}

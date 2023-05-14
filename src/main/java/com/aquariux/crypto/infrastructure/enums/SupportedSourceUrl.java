package com.aquariux.crypto.infrastructure.enums;

public enum SupportedSourceUrl {
    BINANCE("https://api.binance.com/api/v3/ticker/bookTicker"),
    HOUBI("https://api.huobi.pro/market/tickers")
    ;

    private final String url;

    SupportedSourceUrl(final String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}

package com.aquariux.crypto.infrastructure.utils;

public class Constants {
    public static final String DEFAULT_USER_ID = "1";
    public static final int MAX_AGGREDATION_THREAD = 50;

    public static final String[] SupportedTradingPair = {
        "ETHUSDT", 
        "BTCUSDT"
    };

    public static final String[] SupportedSourceUrl = {
        "https://api.binance.com/api/v3/ticker/bookTicker", 
        "https://api.huobi.pro/market/tickers"
    };

    public enum TradingType {
        SELL(0), BUY(1);

        private int type;

        TradingType(int type) {
            this.type = type;
        }

        public int type() {
            return type;
        }
    }
}

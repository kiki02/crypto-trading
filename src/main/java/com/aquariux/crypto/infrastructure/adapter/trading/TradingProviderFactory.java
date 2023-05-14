package com.aquariux.crypto.infrastructure.adapter.trading;

import org.apache.commons.lang3.StringUtils;

import com.aquariux.crypto.infrastructure.adapter.trading.binance.BinanceServiceProvider;
import com.aquariux.crypto.infrastructure.adapter.trading.huobi.HuobiServiceProvider;
import com.aquariux.crypto.infrastructure.enums.SupportedSourceUrl;

public class TradingProviderFactory {

    public TradingProvider getTradingProvider(String sourceUrl){
        if(StringUtils.isBlank(sourceUrl)) {
           return null;
        }

        if (sourceUrl.equals(SupportedSourceUrl.BINANCE.toString())) {
            return new BinanceServiceProvider();
        } else if (sourceUrl.equals(SupportedSourceUrl.HOUBI.toString())) {
            return new HuobiServiceProvider();
        }

        return null;
    }
}

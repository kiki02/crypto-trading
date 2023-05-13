package com.aquariux.crypto.scheduler.aggregation.worker;

import java.math.BigDecimal;
import java.util.Date;

import org.jsoup.Jsoup;

import com.aquariux.crypto.domain.aggregation.PriceAggregation;
import com.aquariux.crypto.domain.aggregation.service.PriceAggregationService;
import com.aquariux.crypto.infrastructure.utils.Constants;

public class PriceAggregationWorker implements Runnable {

    private String tradingPair;
    private PriceAggregationService priceAggregationService;

    public PriceAggregationWorker(String tradingPair, PriceAggregationService priceAggregationService) {
        this.tradingPair = tradingPair;
        this.priceAggregationService = priceAggregationService;
    }

    @Override
    public void run() {
        try {
            PriceAggregation priceAggregation = priceAggregationService.findByTradingPair(tradingPair);
            if (priceAggregation == null) {
                priceAggregation = new PriceAggregation();
                priceAggregation.setTradingPair(tradingPair);
                priceAggregation.setCreateDate(new Date());

                priceAggregation.setBidPrice(new BigDecimal("0"));
                priceAggregation.setAskPrice(new BigDecimal("0"));
            }
            
            for (String sourceUrl : Constants.SupportedSourceUrl) {
                try {
                    String jsonResp = Jsoup.connect(sourceUrl)
                        .maxBodySize(0)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36")
                        .timeout(10 * 60 * 1000)
                        .execute().body();
                    // todo: parsing the json response
                } catch (Exception es) {
                    es.printStackTrace();
                    continue;
                }
            }
            
            priceAggregationService.save(priceAggregation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

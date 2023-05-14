package com.aquariux.crypto.scheduler.aggregation.worker;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.aquariux.crypto.domain.aggregation.PriceAggregation;
import com.aquariux.crypto.domain.aggregation.service.PriceAggregationService;
import com.aquariux.crypto.infrastructure.enums.SupportedSourceUrl;
import com.aquariux.crypto.infrastructure.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            boolean isUpdate = false;
            PriceAggregation priceAggregation = priceAggregationService.findByTradingPair(tradingPair);
            if (priceAggregation == null) {
                priceAggregation = new PriceAggregation();
                priceAggregation.setTradingPair(tradingPair);
                priceAggregation.setCreateDate(new Date());

                priceAggregation.setBidPrice(null);
                priceAggregation.setAskPrice(null);
            } else {
                priceAggregation.setUpdateDate(new Date());
            }

            for (SupportedSourceUrl sourceUrl : SupportedSourceUrl.values()) {
                try {
                    String jsonResp = Jsoup.connect(sourceUrl.toString())
                        .maxBodySize(0)
                        .userAgent(Constants.DEFAULT_USER_AGENT)
                        .header("Accept", "application/json")
                        .timeout(10 * 60 * 1000)
                        .ignoreContentType(true)
                        .execute().body();
                    if (StringUtils.isNotBlank(jsonResp)) {
                        String symbolName = null;
                        String bidPriceName = null;
                        String askPriceName = null;
                        JSONArray jsonArray = null;
                        
                        switch (sourceUrl) {
                            case BINANCE:
                                symbolName = "symbol";
                                bidPriceName = "bidPrice";
                                askPriceName = "askPrice";
                                jsonArray = new JSONArray(jsonResp);
                                break;
                            case HOUBI:
                                symbolName = "symbol";
                                bidPriceName = "bid";
                                askPriceName = "ask";
                                
                                JSONObject huobiObj = new JSONObject(jsonResp);
                                if (huobiObj.has("status") && "ok".equals(huobiObj.getString("status"))
                                        && huobiObj.has("data")) {
                                    jsonArray = huobiObj.getJSONArray("data");
                                } else {
                                    throw new RuntimeException("Failed to load HOUBI URL!. URL = " + sourceUrl.toString());
                                }
                                break;
                            default:
                                throw new RuntimeException("Unsupported source URL!. URL = " + sourceUrl.toString());
                        }
                        
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String pairSymbol = object.getString(symbolName).toUpperCase();
                            if (tradingPair.equals(pairSymbol)) {
                                if (object.has(bidPriceName)) {
                                    BigDecimal bidPrice = null;
                                    switch (sourceUrl) {
                                        case BINANCE:
                                            bidPrice = new BigDecimal(object.getString(bidPriceName));
                                            break;
                                        case HOUBI:
                                            bidPrice = object.getBigDecimal(bidPriceName);
                                            break;
                                        default:
                                            throw new RuntimeException("Unsupported source URL!. URL = " + sourceUrl.toString());
                                    }
                                    
                                    if (priceAggregation.getBidPrice() == null || bidPrice.compareTo(priceAggregation.getBidPrice()) > 0) {
                                        isUpdate = true;
                                        priceAggregation.setBidPrice(bidPrice);
                                        priceAggregation.setBidSource(sourceUrl.toString());
                                    }
                                }
                                if (object.has(askPriceName)) {
                                    BigDecimal askPrice = null;
                                    switch (sourceUrl) {
                                        case BINANCE:
                                            askPrice = new BigDecimal(object.getString(askPriceName));
                                            break;
                                        case HOUBI:
                                            askPrice = object.getBigDecimal(askPriceName);
                                            break;
                                        default:
                                            throw new RuntimeException("Unsupported source URL!. URL = " + sourceUrl.toString());
                                    }

                                    if (priceAggregation.getAskPrice() == null || askPrice.compareTo(priceAggregation.getAskPrice()) < 0) {
                                        isUpdate = true;
                                        priceAggregation.setAskPrice(askPrice);
                                        priceAggregation.setAskSource(sourceUrl.toString());
                                    }
                                }
                                break;
                            }
                        }
                    }
                } catch (Exception es) {
                    log.error(es.getMessage(), es);
                    continue;
                }
            }
            
            if (isUpdate) {
                priceAggregationService.save(priceAggregation);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    
}

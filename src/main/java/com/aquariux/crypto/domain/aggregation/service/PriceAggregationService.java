package com.aquariux.crypto.domain.aggregation.service;

import com.aquariux.crypto.domain.aggregation.PriceAggregation;

public interface PriceAggregationService {
    PriceAggregation findByTradingPair(String tradingPair);
    PriceAggregation save(PriceAggregation priceAggregation);
}

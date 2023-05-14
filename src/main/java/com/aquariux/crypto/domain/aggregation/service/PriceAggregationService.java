package com.aquariux.crypto.domain.aggregation.service;

import java.util.List;

import com.aquariux.crypto.domain.aggregation.PriceAggregation;

public interface PriceAggregationService {
    List<PriceAggregation> findAll();
    PriceAggregation findByTradingPair(String tradingPair);
    PriceAggregation save(PriceAggregation priceAggregation);
}

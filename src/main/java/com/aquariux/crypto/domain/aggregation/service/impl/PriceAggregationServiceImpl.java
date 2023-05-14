package com.aquariux.crypto.domain.aggregation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aquariux.crypto.domain.aggregation.PriceAggregation;
import com.aquariux.crypto.domain.aggregation.repository.PriceAggregationRepository;
import com.aquariux.crypto.domain.aggregation.service.PriceAggregationService;

@Service
public class PriceAggregationServiceImpl implements PriceAggregationService {

    @Autowired
    private PriceAggregationRepository priceAggregationRepository;

    @Override
    public List<PriceAggregation> findAll() {
        return priceAggregationRepository.findAll(Sort.by("tradingPair").ascending());
    }

    @Override
    public PriceAggregation findByTradingPair(String tradingPair) {
        return priceAggregationRepository.findByTradingPair(tradingPair);
    }

    @Override
    public PriceAggregation save(PriceAggregation priceAggregation) {
        return priceAggregationRepository.save(priceAggregation);
    }

}

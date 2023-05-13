package com.aquariux.crypto.domain.aggregation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aquariux.crypto.domain.aggregation.PriceAggregation;

@Repository
public interface PriceAggregationRepository extends JpaRepository<PriceAggregation, String> {
    
    PriceAggregation findByTradingPair(String tradingPair);
}

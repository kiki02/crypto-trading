package com.aquariux.crypto.domain.aggregation;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aquariux.crypto.infrastructure.object.AbstractEntity;

import lombok.Data;

@Entity
@Table(name = "t_price_aggregation")
@Data
public class PriceAggregation extends AbstractEntity {
    @Id
    @Column(name = "aggregation_id", nullable = false)
    private String aggregationId;

    @Column(nullable = false)
    private String sourcePrice;

    @Column(nullable = false)
    private String tradingPair; // ETHUSDT, BTCUSDT

    @Column
    private BigDecimal bidPrice;

    @Column
    private BigDecimal askPrice;

    public PriceAggregation() {
        this.aggregationId = UUID.randomUUID().toString();
    }
}

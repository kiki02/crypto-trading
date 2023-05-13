package com.aquariux.crypto.domain.trading;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aquariux.crypto.infrastructure.object.AbstractEntity;

import lombok.Data;

@Entity
@Table(name = "t_trading_history")
@Data
public class TradingHistory extends AbstractEntity {
    
    @Id
    @Column(name = "trading_id", nullable = false)
    private String tradingId;

    @Column
    private int tradingType; // 0 - SELL, 1 - BUY

    @Column(nullable = false)
    private String tradingPair; // ETHUSDT, BTCUSDT

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal total; // total = amount * price

    @Column
    private BigDecimal oldusdt; // balance before trading

    @Column
    private BigDecimal usdt; // balance after trading

    public TradingHistory() {
        this.tradingId = UUID.randomUUID().toString();
    }
}

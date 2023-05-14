package com.aquariux.crypto.domain.trading;

import java.math.BigDecimal;
import java.util.Date;
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
    private int tradingStatus;

    @Column
    private String requestId;

    @Column
    private Date requestTime;

    @Column
    private int resultCode;

    @Column
    private String resultMessage;

    @Column
    private int tradingType; // 0 - SELL, 1 - BUY

    @Column(nullable = false)
    private String tradingPair; // ETHUSDT, BTCUSDT

    private String tradingSource;

    private BigDecimal price; // the latest best aggregated price.    

    private BigDecimal amount;  // trading amount

    @Column(nullable = false)
    private BigDecimal total; // total = amount * price (USDT)

    @Column
    private BigDecimal usdt; // balance after trading

    @Column
    private BigDecimal eth; // balance after trading

    @Column
    private BigDecimal btc; // balance after trading

    public TradingHistory() {
        this.tradingId = UUID.randomUUID().toString();
    }

    public TradingHistory(TradingRequest tradingRequest) {
        this.tradingId = UUID.randomUUID().toString();
        this.requestId = tradingRequest.getRequestId();
        this.requestTime = tradingRequest.getRequestTime();
        this.tradingType = tradingRequest.getTradingType();
        this.tradingPair = tradingRequest.getTradingPair();
        this.amount = tradingRequest.getAmount();
        this.createDate = new Date();
    }
}

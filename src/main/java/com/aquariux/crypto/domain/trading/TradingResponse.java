package com.aquariux.crypto.domain.trading;

import java.util.Date;

import lombok.Data;

@Data
public class TradingResponse {

    private int resultCode;
    private String resultMessage;

    private String requestId;
    private Date requestTime;
    
    private String tradingId;
    private Date tradingTime;
}

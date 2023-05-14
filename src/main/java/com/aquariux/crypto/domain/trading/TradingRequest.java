package com.aquariux.crypto.domain.trading;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class TradingRequest {

    @NotBlank
    private String requestId;

    private Date requestTime;

    @Min(value = 0)
    @Max(value = 1)
    private int tradingType;

    @NotBlank
    private String tradingPair;

    @Positive
    private BigDecimal amount;
}

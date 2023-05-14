package com.aquariux.crypto.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aquariux.crypto.domain.aggregation.PriceAggregation;
import com.aquariux.crypto.domain.aggregation.service.PriceAggregationService;

@RestController
@RequestMapping("/api/aggregation")
public class PriceAggregationAPIController {
    
    @Autowired
    private PriceAggregationService priceAggregationService;

    @RequestMapping(value = "/price-list", method = RequestMethod.GET)
    public ResponseEntity<Object> getAggregatedPrice() {
        List<PriceAggregation> aggregations = priceAggregationService.findAll();
        return new ResponseEntity<>(aggregations, HttpStatus.OK);
    }
}

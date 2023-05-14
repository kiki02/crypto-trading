package com.aquariux.crypto.aggregation;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.aquariux.crypto.CryptoTradingApplicationTest;

public class PriceAggregationAPITest extends CryptoTradingApplicationTest {
    
    @Test
    public void testAggregation() throws Exception {
        // wait for aggregation scheduler
        TimeUnit.SECONDS.sleep(20);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/api/aggregation/price-list").accept(
				MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.*").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            ;
    }

}

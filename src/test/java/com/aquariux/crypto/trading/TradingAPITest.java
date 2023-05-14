package com.aquariux.crypto.trading;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.aquariux.crypto.CryptoTradingApplicationTest;

// Test flow: create -> check history -> check balance
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TradingAPITest extends CryptoTradingApplicationTest {

    public TradingAPITest() throws InterruptedException {
        // wait for aggregation scheduler
        TimeUnit.SECONDS.sleep(20);
    }
    
    @Test
    public void _1_testCreateTradingBuyDone() throws Exception {
        JSONObject bodyResquestObj = new JSONObject("{\"requestId\": \"a9006ed9-032e-42a5-920b-1\",\"requestTime\": 1684041646666,\"tradingType\": 1,\"tradingPair\": \"ETHUSDT\",\"amount\": 0.55}");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/trading/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyResquestObj.toString())
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultMessage").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.requestId").value("a9006ed9-032e-42a5-920b-1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.tradingId").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.tradingTime").isNotEmpty())
            ;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/trading/history")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.*").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].requestId").value("a9006ed9-032e-42a5-920b-1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(0.55))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].tradingType").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].tradingPair").value("ETHUSDT"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].tradingStatus").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].resultCode").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].resultMessage").value("OK"))
            ;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/wallet/balance")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.walletId").value("cc99c2cc-4649-468e-980b-6d48e6c433b4"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("cc99c2cc-4649-468e-980b-6339463c00ff"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.usdt").isNumber())
            .andExpect(MockMvcResultMatchers.jsonPath("$.eth").value(0.55))
            .andExpect(MockMvcResultMatchers.jsonPath("$.btc").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.createUser").value("cc99c2cc-4649-468e-980b-6339463c00ff"));
    }

    @Test
    public void _2_testCreateTradingBuyFailed() throws Exception {

        JSONObject bodyResquestObj = new JSONObject("{\"requestId\": \"a9006ed9-032e-42a5-920b-2\",\"requestTime\": 1684041646666,\"tradingType\": 1,\"tradingPair\": \"BTCUSDT\",\"amount\": 1111.55}");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/trading/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyResquestObj.toString())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultMessage").value("USDT is not enough."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.requestId").value("a9006ed9-032e-42a5-920b-2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.tradingId").isEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.tradingTime").isEmpty())
            ;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/trading/history")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.*").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
            ;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/wallet/balance")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.walletId").value("cc99c2cc-4649-468e-980b-6d48e6c433b4"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("cc99c2cc-4649-468e-980b-6339463c00ff"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.usdt").isNumber())
            .andExpect(MockMvcResultMatchers.jsonPath("$.eth").value(0.55))
            .andExpect(MockMvcResultMatchers.jsonPath("$.btc").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.createUser").value("cc99c2cc-4649-468e-980b-6339463c00ff"));
    }

    @Test
    public void _3_testCreateTradingSellDone() throws Exception {

        JSONObject bodyResquestObj = new JSONObject("{\"requestId\": \"a9006ed9-032e-42a5-920b-3\",\"requestTime\": 1684041646666,\"tradingType\": 0,\"tradingPair\": \"ETHUSDT\",\"amount\": 0.50}");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/trading/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyResquestObj.toString())
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultMessage").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.requestId").value("a9006ed9-032e-42a5-920b-3"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.tradingId").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.tradingTime").isNotEmpty())
            ;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/trading/history")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.*").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].requestId").value("a9006ed9-032e-42a5-920b-3"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(0.50))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].tradingType").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].tradingPair").value("ETHUSDT"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].tradingStatus").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].resultCode").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].resultMessage").value("OK"))
            ;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/wallet/balance")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.walletId").value("cc99c2cc-4649-468e-980b-6d48e6c433b4"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("cc99c2cc-4649-468e-980b-6339463c00ff"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.usdt").isNumber())
            .andExpect(MockMvcResultMatchers.jsonPath("$.eth").value(0.05))
            .andExpect(MockMvcResultMatchers.jsonPath("$.btc").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.createUser").value("cc99c2cc-4649-468e-980b-6339463c00ff"));
    }

    @Test
    public void _4_testCreateTradingSellFailed() throws Exception {

        JSONObject bodyResquestObj = new JSONObject("{\"requestId\": \"a9006ed9-032e-42a5-920b-4\",\"requestTime\": 1684041646666,\"tradingType\": 0,\"tradingPair\": \"ETHUSDT\",\"amount\": 1111.55}");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/trading/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyResquestObj.toString())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultMessage").value("ETH is not enough."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.requestId").value("a9006ed9-032e-42a5-920b-4"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.tradingId").isEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.tradingTime").isEmpty())
            ;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/trading/history")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.*").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            ;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/wallet/balance")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.walletId").value("cc99c2cc-4649-468e-980b-6d48e6c433b4"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("cc99c2cc-4649-468e-980b-6339463c00ff"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.usdt").isNumber())
            .andExpect(MockMvcResultMatchers.jsonPath("$.eth").value(0.05))
            .andExpect(MockMvcResultMatchers.jsonPath("$.btc").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.createUser").value("cc99c2cc-4649-468e-980b-6339463c00ff"));
    }

}

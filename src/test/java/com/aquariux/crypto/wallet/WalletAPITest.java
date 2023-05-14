package com.aquariux.crypto.wallet;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.aquariux.crypto.CryptoTradingApplicationTest;

public class WalletAPITest extends CryptoTradingApplicationTest {

  @Test
  public void testWallet() throws Exception {

      mockMvc.perform(MockMvcRequestBuilders
              .get("/api/wallet/balance")
              .accept(MediaType.APPLICATION_JSON)
          )
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.walletId").value("cc99c2cc-4649-468e-980b-6d48e6c433b4"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("cc99c2cc-4649-468e-980b-6339463c00ff"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.usdt").isNumber())
          .andExpect(MockMvcResultMatchers.jsonPath("$.eth").isNumber())
          .andExpect(MockMvcResultMatchers.jsonPath("$.btc").value(0))
          .andExpect(MockMvcResultMatchers.jsonPath("$.createUser").value("cc99c2cc-4649-468e-980b-6339463c00ff"));

  }

}

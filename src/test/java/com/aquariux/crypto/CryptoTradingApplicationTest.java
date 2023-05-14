package com.aquariux.crypto;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class CryptoTradingApplicationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Rule 
    public TestName testName = new TestName();

    @Before
    public void init() throws Exception {
        System.out.println("Executing the test funtion: " + testName.getMethodName());
    }

    @After
    public void release() {
    }
}

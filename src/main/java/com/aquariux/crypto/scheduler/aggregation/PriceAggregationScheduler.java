package com.aquariux.crypto.scheduler.aggregation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aquariux.crypto.domain.aggregation.service.PriceAggregationService;
import com.aquariux.crypto.infrastructure.utils.Constants;
import com.aquariux.crypto.scheduler.aggregation.worker.PriceAggregationWorker;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PriceAggregationScheduler {
    
    @Autowired
    private PriceAggregationService priceAggregationService;

    @Scheduled(initialDelay = 10 * 1000, fixedDelay = 10 * 1000)
	public void spyThread() {
        try {
            log.info("PriceAggregationScheduler runs {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            
            ExecutorService executor = Executors.newFixedThreadPool(Constants.MAX_AGGREDATION_THREAD);

            for (String tradingPair : Constants.SupportedTradingPair) {
                Runnable worker = new PriceAggregationWorker(tradingPair, priceAggregationService);
                executor.execute(worker);
            }

            executor.shutdown();
            // Wait until all threads are finish
            while (!executor.isTerminated()) {
                // Running ...
            }
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException ex) {
                executor.shutdownNow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}

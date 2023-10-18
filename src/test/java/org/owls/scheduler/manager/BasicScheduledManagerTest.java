package org.owls.scheduler.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.owls.scheduler.vo.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.Assertions.assertThat;

public class BasicScheduledManagerTest {

    private static final Logger logger = LoggerFactory.getLogger(BasicScheduledManagerTest.class);
    private static AtomicInteger onetimeCounter;
    private ScheduleManager oneTimeSut;

    private static final class TestRunner implements Runnable {
        @Override
        public void run() {
            int count = BasicScheduledManagerTest.onetimeCounter.incrementAndGet();
            BasicScheduledManagerTest.logger.info("{}, count: {}", new Date(), count);
        }
    }

    @BeforeEach
    public void setupEach() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        BasicScheduledManagerTest.onetimeCounter = new AtomicInteger(0);
        this.oneTimeSut = new BasicSchedulerManager(executorService);
    }

    @Test
    public void testStart() {
        int runnerCount = 10;
        long interval = 2000;
        for(int i = 0; i < runnerCount; i++) {
            String threadName = String.format("testRunner-%d", i);
            this.oneTimeSut.registerSchedule(threadName, new Runner(
                    new TestRunner(),
                    interval,
                    System.currentTimeMillis()
            ));
        }
        this.oneTimeSut.start();
        this.oneTimeSut.stop();
        int actualCount = BasicScheduledManagerTest.onetimeCounter.get();
        assertThat(actualCount).isEqualTo(runnerCount);
    }

    @AfterEach
    public void shutdownEach() {
        this.oneTimeSut.stop();
    }
}

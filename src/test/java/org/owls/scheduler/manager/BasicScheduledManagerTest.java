package org.owls.scheduler.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.owls.scheduler.vo.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Set;
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
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        BasicScheduledManagerTest.onetimeCounter = new AtomicInteger(0);
        this.oneTimeSut = new BasicSchedulerManager(executorService);
    }

    @Test
    public void testStart() throws Exception {
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
        Set<String> listedSchedules = this.oneTimeSut.listSchedule();
        assertThat(listedSchedules).hasSize(10);
        assertThat(listedSchedules).containsExactlyInAnyOrder(
            "testRunner-0", "testRunner-1", "testRunner-2",
                    "testRunner-3", "testRunner-4", "testRunner-5",
                    "testRunner-6", "testRunner-7", "testRunner-8",
                    "testRunner-9"
                );
        Thread.sleep(2000L + 1);
        assertThat(this.oneTimeSut.getStatus()).isEqualTo(ManagerStatus.RUNNING);
        this.oneTimeSut.stop();
        int actualCount = BasicScheduledManagerTest.onetimeCounter.get();
        assertThat(actualCount).isEqualTo(runnerCount);
    }

    @AfterEach
    public void shutdownEach() {
        this.oneTimeSut.stop();
    }
}

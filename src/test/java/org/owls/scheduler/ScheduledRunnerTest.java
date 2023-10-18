package org.owls.scheduler;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.Assertions.assertThat;

public class ScheduledRunnerTest {

    public static final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger logger = LoggerFactory.getLogger(ScheduledRunnerTest.class);

    @Test
    public void testRun() throws Exception {
        ScheduledRunner<Integer> sut = new ScheduledRunner<>(param -> {
            int incremented = 0;
            for (int i  = 0; i < param; i++) {
                incremented = ScheduledRunnerTest.counter.incrementAndGet();
            }
            logger.info("Thread Id: [{}] - Increased Value: [{}]", Thread.currentThread().getId(), incremented);
        }, 2);

        int threadCount = 10;
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < threadCount; i++) {
            Thread t = new Thread(sut, String.format("Thread%d", i));
            threads.add(t);
            t.start();
        }

        for(Thread thread : threads) {
            thread.join();
        }
        logger.info("increased count: {}", counter.get());
        int actual = counter.get();
        assertThat(actual).isEqualTo(20);
    }
}

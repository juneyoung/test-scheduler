package org.owls.scheduler.manager;

import org.junit.jupiter.api.Test;
import org.owls.scheduler.manager.outer.DomainAccessCounter;
import org.owls.scheduler.manager.outer.StatisticsReporter;
import org.owls.scheduler.vo.Runner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IntegrationTest {

    @Test
    public void test() throws Exception{
        DomainAccessCounter domainAccessCounter = DomainAccessCounter.getInstance();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ScheduleManager sut = new BasicSchedulerManager(executorService);
        long interval = 1000L;

        StatisticsReporter runnable = new StatisticsReporter(domainAccessCounter);
        Runner runner = new Runner(runnable, interval, System.currentTimeMillis());
        sut.registerSchedule("test", runner);
        sut.start();
        Thread.sleep(5000L + 1);
        sut.stop();
    }
}

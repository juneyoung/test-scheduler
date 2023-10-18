package org.owls.scheduler.manager.outer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsReporter implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsReporter.class);
    private final DomainAccessCounter domainAccessCounter;

    public StatisticsReporter(DomainAccessCounter domainAccessCounter) {
        System.out.println("==> constructor on DomainAccessCounter :: " + domainAccessCounter);
        this.domainAccessCounter = domainAccessCounter;
    }

    @Override
    public void run() {
        // TODO: 실제로는 여기서 connection 도 맺고 끊는다
        System.out.println("===> running start");
        for (String statisticName : this.domainAccessCounter.getLabels()) {
            int countedValue = this.domainAccessCounter.getCount(statisticName);
            StatisticsReporter.logger.info("===> Collect data Stats: {}, value: {}", statisticName, countedValue);
        }
    }
}

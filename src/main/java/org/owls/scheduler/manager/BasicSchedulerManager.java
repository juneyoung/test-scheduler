package org.owls.scheduler.manager;


import org.owls.scheduler.vo.Runner;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class BasicSchedulerManager implements ScheduleManager {
    private ManagerStatus status;
    private final Map<String, Runner> runnerByName;
    private final ExecutorService executorService;
    private final Thread backgroundMonitor;
    private static final long BASE_INTERVAL_MILLIS = 1000L;

    public BasicSchedulerManager(ExecutorService executorService) {
        this.executorService = executorService;
        this.runnerByName = new ConcurrentHashMap<>();
        this.status = ManagerStatus.TERMINATED;
        this.backgroundMonitor = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    long nowInEpochSeconds = System.currentTimeMillis()/1000L;
                    for (Map.Entry<String, Runner> entry : this.runnerByName.entrySet()) {
                        String runnableName = entry.getKey();
                        Runner r = entry.getValue();
                        // 실행 조건에 맞으면 executorService.submit
                        long lastExecutedInSeconds = r.getLastExecutedEpochSeconds();
                        long intervalInSeconds = r.getIntervalInMilliSeconds()/1000L;
                        if (nowInEpochSeconds == (lastExecutedInSeconds + intervalInSeconds)){
                            Runnable runnable = r.updateLastExecutionAndGetRunnable();
                            System.out.println("===> " + new Date(nowInEpochSeconds * 1000) + " submit Runnable name : " + runnableName);
                            this.executorService.submit(runnable);
                        }
                    }
                    //noinspection BusyWait
                    Thread.sleep(BASE_INTERVAL_MILLIS);
                } catch (InterruptedException e) {
                    System.out.println("===> Monitor interrupted");
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void registerSchedule(String name, Runner runner) {
        this.runnerByName.put(name, runner);
    }

    @Override
    public void removeSchedule(String name) {
        this.runnerByName.remove(name);
    }

    @Override
    public void start() {
        if (ManagerStatus.RUNNING != this.status) {
            this.status = ManagerStatus.RUNNING;
            this.backgroundMonitor.start();
        }
    }

    @Override
    public void stop() {
        while(!this.executorService.isShutdown()) {
            this.executorService.shutdown();
        }
        this.status = ManagerStatus.TERMINATED;
        System.out.println("SchedulerManager has been stopped: " + this.executorService.isShutdown());
    }

    @Override
    public Set<String> listSchedule() {
        return this.runnerByName.keySet();
    }

    @Override
    public ManagerStatus getStatus() {
        return this.status;
    }
}

package org.owls.scheduler.manager;

import org.owls.scheduler.ScheduledFunction;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BasicSchedulerManager implements ScheduleManager {
    private ManagerStatus managerStatus;
    private final Map<String, ScheduledFunction<?>> scheduledFunctionByScheduleName;
    private final ExecutorService executorService;
    private final int FIXED_THREAD_POOL = 10;

    public BasicSchedulerManager() {
        this.scheduledFunctionByScheduleName = new ConcurrentHashMap<>();
        this.executorService = Executors.newFixedThreadPool(FIXED_THREAD_POOL);
    }

    @Override
    public <T> void registerSchedule(String name, String cronExpression, ScheduledFunction<T> scheduledFunction) {
        this.scheduledFunctionByScheduleName.put(name, scheduledFunction);
    }

    @Override
    public void removeSchedule(String name) {
        this.scheduledFunctionByScheduleName.remove(name);
    }

    @Override
    public Set<String> listSchedule() {
        return this.scheduledFunctionByScheduleName.keySet();
    }

    @Override
    public void start() {
        if (this.managerStatus != ManagerStatus.RUNNING) {
            // thread 돌면서 주기가 맞으면 execute / 이전 잡이 안 끝났으면?
            this.managerStatus = ManagerStatus.RUNNING;
            // TODO: 루프 돌면서 executorService 에 submit 한다.
        }
    }

    @Override
    public void stop() {
        this.managerStatus = ManagerStatus.TERMINATED;
        // TODO: 모니터 중지
    }

    @Override
    public ManagerStatus getStatus() {
        return this.managerStatus;
    }
}

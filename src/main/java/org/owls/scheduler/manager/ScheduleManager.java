package org.owls.scheduler.manager;

import org.owls.scheduler.ScheduledFunction;

import java.util.Set;

public interface ScheduleManager {
    <T> void registerSchedule(String name, String cronExpression, ScheduledFunction<T> scheduledFunction);
    void removeSchedule(String name);
    void start(); // initialize
    void stop(); // destroy
    Set<String> listSchedule();
    ManagerStatus getStatus();
}

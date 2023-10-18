package org.owls.scheduler.manager;

import org.owls.scheduler.vo.Runner;

import java.util.Set;

public interface ScheduleManager {
    void registerSchedule(String name, Runner runner);
    void removeSchedule(String name);
    void start(); // initialize
    void stop(); // destroy
    Set<String> listSchedule();
    ManagerStatus getStatus();
}

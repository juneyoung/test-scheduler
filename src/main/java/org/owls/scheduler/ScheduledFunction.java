package org.owls.scheduler;

public interface ScheduledFunction<P> {
    void execute(P param);
}

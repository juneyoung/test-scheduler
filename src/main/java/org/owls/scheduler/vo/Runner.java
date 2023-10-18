package org.owls.scheduler.vo;

import java.util.Objects;

public class Runner {
    private final Runnable runnable; // logic to execute
    private final long intervalInMilliSeconds; // interval between each task
    private final long issued; // task issued date
    private long lastExecutedEpochSeconds;

    public Runner(Runnable runnable, long intervalInMilliSeconds, long issued) {
        this.runnable = runnable;
        this.intervalInMilliSeconds = intervalInMilliSeconds;
        this.issued = issued;
        this.lastExecutedEpochSeconds = issued/1000L;
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

    public Runnable updateLastExecutionAndGetRunnable() {
        this.lastExecutedEpochSeconds = System.currentTimeMillis()/1000L;
        return this.runnable;
    }

    public long getIntervalInMilliSeconds() {
        return this.intervalInMilliSeconds;
    }

    public long getIssued() {
        return this.issued;
    }

    public long getLastExecutedEpochSeconds() {
        return this.lastExecutedEpochSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Runner runner = (Runner) o;
        return intervalInMilliSeconds == runner.intervalInMilliSeconds && issued == runner.issued && lastExecutedEpochSeconds == runner.lastExecutedEpochSeconds && Objects.equals(runnable, runner.runnable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(runnable, intervalInMilliSeconds, issued, lastExecutedEpochSeconds);
    }

    @Override
    public String toString() {
        return "Runner{" +
                "runnable=" + runnable +
                ", intervalInMilliSeconds=" + intervalInMilliSeconds +
                ", issued=" + issued +
                ", lastExecutedEpochSeconds=" + lastExecutedEpochSeconds +
                '}';
    }
}

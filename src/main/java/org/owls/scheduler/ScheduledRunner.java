package org.owls.scheduler;

public final class ScheduledRunner<P> implements Runnable {

    private final ScheduledFunction<P> executorProxy;
    private final P param;

    public ScheduledRunner(ScheduledFunction<P> executorProxy, P param) {
        this.executorProxy = executorProxy;
        this.param = param;
    }

    @Override
    public void run() {
        this.executorProxy.execute(param);
    }
}

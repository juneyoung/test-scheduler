package org.owls.scheduler.manager.outer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DomainAccessCounter implements Counter {

    private final Map<String, AtomicInteger> counter;
    private DomainAccessCounter(){
        this.counter = new ConcurrentHashMap<>();
    }
    private static final DomainAccessCounter INSTANCE = new DomainAccessCounter();

    public static DomainAccessCounter getInstance() {
        System.out.println("===> Call DomainAccessCounter.getInstance");
        return DomainAccessCounter.INSTANCE;
    }


    @Override
    public void increase(String key) {
        AtomicInteger intValue = this.counter.get(key);
        if (null == intValue) {
            intValue = new AtomicInteger(1);
        } else {
            intValue.incrementAndGet();
        }
        this.counter.put(key, intValue);
    }

    @Override
    public void decrease(String key) {
        AtomicInteger intValue = this.counter.get(key);
        if (null != intValue) {
            intValue.decrementAndGet();
        }
        this.counter.put(key, intValue);
    }

    @Override
    public void initialize(String key) {
        this.counter.remove(key);
    }

    @Override
    public int getCount(String key) {
        return (null == this.counter.get(key)) ? 0 : this.counter.get(key).intValue();
    }

    @Override
    public Set<String> getLabels() {
        return this.counter.keySet();
    }

    @Override
    public AtomicInteger remove(String key) {
        return this.counter.remove(key);
    }
}
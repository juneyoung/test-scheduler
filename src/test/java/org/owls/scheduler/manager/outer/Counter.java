package org.owls.scheduler.manager.outer;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public interface Counter {
    void increase(String key);
    void decrease(String key);
    void initialize(String key);
    int getCount(String key);
    Set<String> getLabels();
    AtomicInteger remove(String key);
}

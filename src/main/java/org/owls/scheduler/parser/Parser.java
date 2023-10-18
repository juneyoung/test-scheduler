package org.owls.scheduler.parser;

public interface Parser<T> {
    T parse(String literal);
}

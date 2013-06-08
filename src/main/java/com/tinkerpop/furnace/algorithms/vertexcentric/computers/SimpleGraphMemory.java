package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.tinkerpop.furnace.algorithms.vertexcentric.GraphSystemMemory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SimpleGraphMemory implements GraphSystemMemory {

    private final Map<String, Object> memory;
    private final AtomicInteger iteration = new AtomicInteger(0);
    private final AtomicLong runtime = new AtomicLong(0l);

    public SimpleGraphMemory() {
        this(new ConcurrentHashMap<String, Object>());
    }

    public SimpleGraphMemory(final Map<String, Object> state) {
        this.memory = state;
    }

    public void incrIteration() {
        this.iteration.getAndIncrement();
    }

    public int getIteration() {
        return this.iteration.get();
    }

    public void setRuntime(final long runTime) {
        this.runtime.set(runTime);
    }

    public long getRuntime() {
        return this.runtime.get();
    }

    public boolean isInitialIteration() {
        return this.getIteration() == 0;
    }

    public <R> R get(final String key) {
        return (R) this.memory.get(key);
    }

    public void increment(final String key, final long delta) {
        final Object value = this.memory.get(key);
        this.memory.put(key, (Long) value + delta);
    }

    public void decrement(final String key, final long delta) {
        final Object value = this.memory.get(key);
        this.memory.put(key, (Long) value - delta);
    }

    public void setIfAbsent(final String key, final Object value) {
        if (this.memory.containsKey(key))
            throw new IllegalStateException("The memory already has the a value for key " + key);
        this.memory.put(key, value);
    }
}

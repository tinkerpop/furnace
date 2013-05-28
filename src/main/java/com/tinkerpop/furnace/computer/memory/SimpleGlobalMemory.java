package com.tinkerpop.furnace.computer.memory;

import com.tinkerpop.furnace.computer.GlobalMemory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SimpleGlobalMemory implements GlobalMemory {

    private final Map<String, Object> memory;

    public SimpleGlobalMemory() {
        this(new ConcurrentHashMap<String, Object>());
    }

    public SimpleGlobalMemory(final Map<String, Object> state) {
        this.memory = state;
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

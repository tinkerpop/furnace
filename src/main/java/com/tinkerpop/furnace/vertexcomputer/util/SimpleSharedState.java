package com.tinkerpop.furnace.vertexcomputer.util;

import com.tinkerpop.furnace.vertexcomputer.SharedState;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SimpleSharedState implements SharedState {

    private Map<String, Object> state = new HashMap<String, Object>();

    /*public void increment(final String key, final long delta) {
        final Object value = this.state.get(key);
        this.state.put(key, (Long) value + delta);
    }

    public void decrement(final String key, final long delta) {
        final Object value = this.state.get(key);
        this.state.put(key, (Long) value - delta);
    }*/

    public <R> R get(final String key) {
        return (R) this.state.get(key);
    }

    public void setIfAbsent(final String key, final Object value) {
        if (this.state.containsKey(key))
            throw new IllegalStateException("The state already has the a value for key " + key);
        this.state.put(key, value);
    }
}

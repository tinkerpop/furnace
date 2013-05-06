package com.tinkerpop.furnace.vertexcompute.util;

import com.tinkerpop.furnace.vertexcompute.SharedState;

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

    public void set(final String key, final Object value) {
        this.state.put(key, value);
    }
}

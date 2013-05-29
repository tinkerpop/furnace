package com.tinkerpop.furnace.computer;

/**
 * Each vertex in a vertex-centric graph computation can access itself and its neighbors.
 * However, in many situations, a global backboard (or distributed cache) is desired.
 * The GlobalMemory is a synchronizing data structure that allows arbitrary vertex communication.
 *
 * @author Matthias Broecheler (me@matthiasb.com)
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface GlobalMemory {

    public <R> R get(final String key);

    public void setIfAbsent(String key, Object value);

    public void increment(String key, long delta);

    public void decrement(String key, long delta);

    public void incrIteration();

    public int getIteration();

    /*public void min(String key, double value);

    public void max(String key, double value);

    public void avg(String key, double value);

    public <V> void update(String key, Function<V, V> update, V defaultValue);*/

    // public Map<String, Object> getCurrentState();
}

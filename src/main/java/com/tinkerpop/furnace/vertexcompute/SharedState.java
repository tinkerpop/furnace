package com.tinkerpop.furnace.vertexcompute;

/**
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public interface SharedState {

    public <R> R get(final String key);

    public void set(final String key, final Object value);

   /*public void increment(String key, long delta);

    public void decrement(String key, long delta);*/

    /*public void min(String key, double value);

    public void max(String key, double value);

    public void avg(String key, double value);

    public <V> void setIfAbsent(String key, V value);

    public <V> void update(String key, Function<V, V> update, V defaultValue);*/

    // public Map<String, Object> getCurrentState();

}

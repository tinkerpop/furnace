package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphComputer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SimpleVertexMemory extends AbstractVertexMemory {

    private final Map<Object, Map<String, Object>> memory;

    public SimpleVertexMemory(final GraphComputer.Isolation isolation) {
        this(isolation, new ConcurrentHashMap<Object, Map<String, Object>>());
    }

    public SimpleVertexMemory(final GraphComputer.Isolation isolation, final Map<Object, Map<String, Object>> memory) {
        super(isolation);
        this.memory = memory;
    }

    public void setProperty(final Vertex vertex, final String key, final Object value) {
        Map<String, Object> map = this.memory.get(vertex.getId());
        if (null == map) {
            map = new HashMap<String, Object>();
            this.memory.put(vertex.getId(), map);
        }
        final String bspKey = generateSetKey(key);
        if (isConstantKey(key) && map.containsKey(bspKey))
            throw new IllegalStateException("The constant property " + bspKey + " has already been set for vertex " + vertex);
        else
            map.put(bspKey, value);
    }

    public <T> T getProperty(final Vertex vertex, final String key) {
        final Map<String, Object> map = this.memory.get(vertex.getId());
        if (null == map)
            return null;
        else
            return (T) map.get(generateGetKey(key));
    }

    public <T> T removeProperty(final Vertex vertex, final String key) {
        final Map<String, Object> map = this.memory.get(vertex.getId());
        if (null == map)
            return null;
        else {
            return (T) map.remove(generateGetKey(key));
        }
    }
}

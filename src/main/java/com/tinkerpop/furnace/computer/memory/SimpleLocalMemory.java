package com.tinkerpop.furnace.computer.memory;

import com.tinkerpop.blueprints.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SimpleLocalMemory extends AbstractLocalMemory {

    private final Map<Object, Map<String, Object>> memory;

    public SimpleLocalMemory() {
        this(new ConcurrentHashMap<Object, Map<String, Object>>());
    }

    public SimpleLocalMemory(final Map<Object, Map<String, Object>> memory) {
        super();
        this.memory = memory;
    }

    public void setProperty(final Vertex vertex, final String key, final Object value) {
        Map<String, Object> map = memory.get(vertex.getId());
        if (null == map) {
            map = new HashMap<String, Object>();
            memory.put(vertex.getId(), map);
        }
        map.put(generateSetKey(key), value);
    }

    public <T> T getProperty(final Vertex vertex, final String key) {
        final Map<String, Object> map = memory.get(vertex.getId());
        if (null == map)
            return null;
        else
            return (T) map.get(generateGetKey(key));
    }

    public String toString() {
        return this.memory.toString();
    }
}

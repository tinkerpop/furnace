package com.tinkerpop.furnace.vertexcomputer.wrappers;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.vertexcomputer.ComputerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class MapComputerProperties implements ComputerProperties {

    private final Map<Object, Map<String, Object>> properties;

    public MapComputerProperties(final Map<Object, Map<String, Object>> properties) {
        this.properties = properties;
    }

    public void setProperty(final Vertex vertex, final String key, final Object value) {
        Map<String, Object> map = properties.get(vertex.getId());
        if (null == map) {
            map = new HashMap<String, Object>();
            properties.put(vertex.getId(), map);
        }
        map.put(key, value);
    }

    public <T> T removeProperty(final Vertex vertex, final String key) {
        final Map<String, Object> map = properties.get(vertex.getId());
        if (null == map)
            return null;
        else
            return (T) map.remove(key);
    }

    public <T> T getProperty(final Vertex vertex, final String key) {
        final Map<String, Object> map = properties.get(vertex.getId());
        if (null == map)
            return null;
        else
            return (T) map.get(key);
    }
}

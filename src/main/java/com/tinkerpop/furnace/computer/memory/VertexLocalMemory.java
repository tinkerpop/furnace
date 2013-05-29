package com.tinkerpop.furnace.computer.memory;

import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class VertexLocalMemory extends AbstractLocalMemory {

    public void setProperty(final Vertex vertex, final String key, final Object value) {
        vertex.setProperty(generateSetKey(key), value);
    }

    public <T> T getProperty(final Vertex vertex, final String key) {
        return vertex.getProperty(generateGetKey(key));
    }
}
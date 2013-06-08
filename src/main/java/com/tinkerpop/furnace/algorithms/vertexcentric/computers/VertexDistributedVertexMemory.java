package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphComputer;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class VertexDistributedVertexMemory extends AbstractVertexMemory {

    public VertexDistributedVertexMemory(final GraphComputer.Isolation isolation) {
        super(isolation);
    }

    public void setProperty(final Vertex vertex, final String key, final Object value) {
        vertex.setProperty(generateSetKey(key), value);
    }

    public <T> T getProperty(final Vertex vertex, final String key) {
        return vertex.getProperty(generateGetKey(key));
    }

    public <T> T removeProperty(final Vertex vertex, final String key) {
        return vertex.removeProperty(generateGetKey(key));
    }
}
package com.tinkerpop.furnace.algorithms.vertexcentric;

import com.tinkerpop.blueprints.Vertex;

/**
 * VertexMemory denotes the vertex properties that are used for the VertexProgram.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface VertexMemory {

    public void setProperty(Vertex vertex, String key, Object value);

    public <T> T getProperty(Vertex vertex, String key);

    public <T> T removeProperty(Vertex vertex, String key);

}

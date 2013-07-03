package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexSystemMemory;

import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class AdjacentShellVertex implements Vertex {

    private final VertexSystemMemory vertexMemory;
    private final Vertex baseVertex;

    public AdjacentShellVertex(final Vertex baseVertex, final VertexSystemMemory vertexMemory) {
        this.baseVertex = baseVertex;
        this.vertexMemory = vertexMemory;
    }

    public Object getId() {
        return this.baseVertex.getId();
    }

    public String toString() {
        return this.baseVertex.toString();
    }

    public boolean equals(Object object) {
        return (object instanceof Vertex) && ((Element) object).getId().equals(this.getId());
    }

    public int hashCode() {
        return this.baseVertex.hashCode();
    }

    public <T> T getProperty(final String key) {
        if (this.vertexMemory.isComputeKey(key))
            return this.vertexMemory.getProperty(this, key);
        else
            throw new IllegalArgumentException("The provided key is not a compute key: " + key);
    }

    public <T> T removeProperty(final String key) {
        throw new UnsupportedOperationException();
    }

    public final Set<String> getPropertyKeys() {
        throw new UnsupportedOperationException();
    }

    public void setProperty(final String key, final Object value) {
        throw new UnsupportedOperationException();
    }

    public Edge addEdge(final String label, final Vertex inVertex) {
        throw new UnsupportedOperationException();
    }

    public Iterable<Edge> getEdges(final Direction direction, final String... labels) {
        throw new UnsupportedOperationException();
    }

    public Iterable<Vertex> getVertices(final Direction direction, final String... labels) {
        throw new UnsupportedOperationException();
    }

    public VertexQuery query() {
        throw new UnsupportedOperationException();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }


}

package com.tinkerpop.furnace.computer.evaluators;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.furnace.computer.LocalMemory;

import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class AdjacentShellVertex implements Vertex {

    private final LocalMemory localMemory;
    private final Vertex baseVertex;

    public AdjacentShellVertex(final Vertex baseVertex, final LocalMemory localMemory) {
        this.baseVertex = baseVertex;
        this.localMemory = localMemory;
    }

    public Object getId() {
        return this.baseVertex.getId();
    }

    public <T> T getProperty(final String key) {
        if (this.localMemory.isComputeKey(key))
            return this.localMemory.getProperty(this, key);
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

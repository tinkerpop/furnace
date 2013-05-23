package com.tinkerpop.furnace.vertexcomputer.wrappers;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.furnace.vertexcomputer.GraphComputer;

import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class LightVertex implements Vertex {

    private final GraphComputer graphComputer;
    private final Vertex baseVertex;

    public LightVertex(final Vertex baseVertex, final GraphComputer graphComputer) {
        this.baseVertex = baseVertex;
        this.graphComputer = graphComputer;
    }

    public Object getId() {
        return this.baseVertex.getId();
    }

    public <T> T getProperty(final String key) {
        if (this.graphComputer.isComputeKey(key))
            return this.graphComputer.getComputerProperties().getProperty(this, key);
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

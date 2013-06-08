package com.tinkerpop.furnace.computer.computers;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.computer.VertexSystemMemory;

import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ShellEdge implements Edge {

    private final VertexSystemMemory vertexMemory;
    private Edge baseEdge;

    public ShellEdge(final Edge baseEdge, final VertexSystemMemory vertexMemory) {
        this.baseEdge = baseEdge;
        this.vertexMemory = vertexMemory;
    }

    public Object getId() {
        return this.baseEdge.getId();
    }

    public String toString() {
        return this.baseEdge.toString();
    }

    public String getLabel() {
        return this.baseEdge.getLabel();
    }

    public Set<String> getPropertyKeys() {
        return this.baseEdge.getPropertyKeys();
    }

    public <R> R removeProperty(final String key) {
        throw new UnsupportedOperationException();
    }

    public <R> R getProperty(final String key) {
        return this.baseEdge.getProperty(key);
    }

    public void setProperty(final String key, final Object value) {
        throw new UnsupportedOperationException();
    }

    public Vertex getVertex(final Direction direction) {
        return new AdjacentShellVertex(this.baseEdge.getVertex(direction), this.vertexMemory);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }


}

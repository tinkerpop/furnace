package com.tinkerpop.furnace.computer.evaluators;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.computer.LocalMemory;

import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ShellEdge implements Edge {

    private final LocalMemory localMemory;
    private Edge baseEdge;

    public ShellEdge(final Edge baseEdge, final LocalMemory localMemory) {
        this.baseEdge = baseEdge;
        this.localMemory = localMemory;
    }

    public Object getId() {
        return this.baseEdge.getId();
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
        return new AdjacentShellVertex(this.baseEdge.getVertex(direction), this.localMemory);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }


}

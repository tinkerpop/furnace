package com.tinkerpop.furnace.computer.managers;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.blueprints.util.wrappers.WrapperVertexQuery;
import com.tinkerpop.furnace.computer.LocalMemory;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class CoreShellVertex implements Vertex {

    private final LocalMemory localMemory;
    private Vertex baseVertex;

    public CoreShellVertex(final LocalMemory localMemory) {
        this.localMemory = localMemory;
    }

    public void setBaseVertex(final Vertex baseVertex) {
        this.baseVertex = baseVertex;
    }

    public Object getId() {
        return baseVertex.getId();
    }

    public Iterable<Vertex> getVertices(final Direction direction, final String... labels) {
        return new LightVertexIterable(this.baseVertex.getVertices(direction, labels));
    }

    public Iterable<Edge> getEdges(final Direction direction, final String... labels) {
        throw new UnsupportedOperationException();
    }

    public <R> R getProperty(final String key) {
        if (this.localMemory.isComputeKey(key))
            return this.localMemory.getProperty(this, key);
        else
            throw new IllegalArgumentException("The provided key is not a compute key: " + key);
    }

    public void setProperty(final String key, final Object value) {
        if (this.localMemory.isComputeKey(key))
            this.localMemory.setProperty(this, key, value);
        else
            throw new IllegalArgumentException("The provided key is not a compute key: " + key);
    }

    public Set<String> getPropertyKeys() {
        return baseVertex.getPropertyKeys();
    }

    public <R> R removeProperty(final String key) {
        if (this.localMemory.isComputeKey(key))
            return this.localMemory.removeProperty(this, key);
        else
            throw new IllegalArgumentException("The provided key is not a compute key: " + key);
    }

    public Edge addEdge(final String label, final Vertex vertex) {
        throw new UnsupportedOperationException();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public VertexQuery query() {
        return new WrapperVertexQuery(this.baseVertex.query()) {
            @Override
            public Iterable<Edge> edges() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Iterable<Vertex> vertices() {
                return new LightVertexIterable(this.query.vertices());
            }
        };
    }

    public class LightVertexIterable implements Iterable<Vertex> {

        private final Iterable<Vertex> iterable;

        public LightVertexIterable(final Iterable<Vertex> iterable) {
            this.iterable = iterable;
        }

        public Iterator<Vertex> iterator() {
            final Iterator<Vertex> itty = iterable.iterator();
            return new Iterator<Vertex>() {

                public void remove() {
                    throw new UnsupportedOperationException();
                }

                public boolean hasNext() {
                    return itty.hasNext();
                }

                public Vertex next() {
                    return new AdjacentShellVertex(itty.next(), localMemory);
                }
            };
        }
    }
}

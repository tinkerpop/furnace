package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.blueprints.util.wrappers.WrapperVertexQuery;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexSystemMemory;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class CoreShellVertex implements Vertex {

    private final VertexSystemMemory vertexMemory;
    private Vertex baseVertex;

    public CoreShellVertex(final VertexSystemMemory vertexMemory) {
        this.vertexMemory = vertexMemory;
    }

    public void setBaseVertex(final Vertex baseVertex) {
        this.baseVertex = baseVertex;
    }

    public Object getId() {
        return baseVertex.getId();
    }

    public String toString() {
        return this.baseVertex.toString();
    }

    public Iterable<Vertex> getVertices(final Direction direction, final String... labels) {
        return new AdjacentShellVertexIterable(this.baseVertex.getVertices(direction, labels));
    }

    public Iterable<Edge> getEdges(final Direction direction, final String... labels) {
        throw new UnsupportedOperationException();
    }

    public <R> R getProperty(final String key) {
        if (this.vertexMemory.isComputeKey(key))
            return this.vertexMemory.getProperty(this, key);
        else
            return this.baseVertex.getProperty(key);
    }

    public void setProperty(final String key, final Object value) {
        if (this.vertexMemory.isComputeKey(key))
            this.vertexMemory.setProperty(this, key, value);
        else
            throw new IllegalArgumentException("The provided key is not a compute key: " + key);
    }

    public Set<String> getPropertyKeys() {
        return baseVertex.getPropertyKeys();
    }

    public <R> R removeProperty(final String key) {
        if (this.vertexMemory.isComputeKey(key))
            return this.vertexMemory.removeProperty(this, key);
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
                return new ShellEdgeIterable(this.query.edges());
            }

            @Override
            public Iterable<Vertex> vertices() {
                return new AdjacentShellVertexIterable(this.query.vertices());
            }
        };
    }

    public class ShellEdgeIterable implements Iterable<Edge> {

        private final Iterable<Edge> iterable;

        public ShellEdgeIterable(final Iterable<Edge> iterable) {
            this.iterable = iterable;
        }

        public Iterator<Edge> iterator() {
            final Iterator<Edge> itty = iterable.iterator();
            return new Iterator<Edge>() {

                public void remove() {
                    throw new UnsupportedOperationException();
                }

                public boolean hasNext() {
                    return itty.hasNext();
                }

                public Edge next() {
                    return new ShellEdge(itty.next(), vertexMemory);
                }
            };
        }
    }

    public class AdjacentShellVertexIterable implements Iterable<Vertex> {

        private final Iterable<Vertex> iterable;

        public AdjacentShellVertexIterable(final Iterable<Vertex> iterable) {
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
                    return new AdjacentShellVertex(itty.next(), vertexMemory);
                }
            };
        }
    }
}

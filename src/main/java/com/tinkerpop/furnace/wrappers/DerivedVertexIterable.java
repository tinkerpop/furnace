package com.tinkerpop.furnace.wrappers;

import com.tinkerpop.blueprints.Vertex;

import java.util.Iterator;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedVertexIterable implements Iterable<Vertex> {

    private final Iterable<Vertex> vertices;
    private final DerivedGraph derivedGraph;

    public DerivedVertexIterable(final Iterable<Vertex> vertices, final DerivedGraph derivedGraph) {
        this.vertices = vertices;
        this.derivedGraph = derivedGraph;
    }

    public Iterator<Vertex> iterator() {
        return new Iterator<Vertex>() {

            private final Iterator<Vertex> itty = vertices.iterator();

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Vertex next() {
                return new DerivedVertex(itty.next(), derivedGraph);
            }

            public boolean hasNext() {
                return this.itty.hasNext();
            }
        };
    }
}

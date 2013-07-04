package com.tinkerpop.furnace.wrappers.derived;

import com.tinkerpop.blueprints.Edge;

import java.util.Iterator;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedEdgeIterable implements Iterable<Edge> {

    private final Iterable<Edge> edges;
    private final DerivedGraph derivedGraph;

    public DerivedEdgeIterable(final Iterable<Edge> edges, final DerivedGraph derivedGraph) {
        this.edges = edges;
        this.derivedGraph = derivedGraph;
    }

    public Iterator<Edge> iterator() {
        return new Iterator<Edge>() {

            private final Iterator<Edge> itty = edges.iterator();

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Edge next() {
                return new DerivedEdge(itty.next(), derivedGraph);
            }

            public boolean hasNext() {
                return this.itty.hasNext();
            }
        };
    }
}
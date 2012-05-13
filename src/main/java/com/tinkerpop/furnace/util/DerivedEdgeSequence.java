package com.tinkerpop.furnace.util;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.DerivedEdge;
import com.tinkerpop.furnace.DerivedGraph;
import com.tinkerpop.furnace.DerivedVertex;

import java.util.Iterator;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedEdgeSequence implements Iterator<Edge>, Iterable<Edge> {

    private final String label;
    private final Vertex outVertex;
    private final Iterator<Vertex> inVertices;
    private final DerivedGraph graph;

    public DerivedEdgeSequence(final DerivedGraph graph, final Vertex outVertex, final Iterator<Vertex> inVertices, final String label) {
        if (outVertex instanceof DerivedVertex)
            this.outVertex = outVertex;
        else
            this.outVertex = new DerivedVertex(outVertex, graph);
        this.inVertices = inVertices;
        this.label = label;
        this.graph = graph;
    }

    public Iterator<Edge> iterator() {
        return this;
    }

    public boolean hasNext() {
        return this.inVertices.hasNext();
    }

    public Edge next() {
        return new DerivedEdge(this.outVertex, new DerivedVertex(this.inVertices.next(), graph), label);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

}

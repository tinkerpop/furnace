package com.tinkerpop.furnace;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedVertex implements Vertex {

    private final Vertex rawVertex;
    private final DerivedGraph graph;

    public DerivedVertex(final Vertex rawVertex, final DerivedGraph graph) {
        this.rawVertex = rawVertex;
        this.graph = graph;
    }

    public Object getId() {
        return this.rawVertex.getId();
    }

    public Object getProperty(final String key) {
        return this.rawVertex.getProperty(key);
    }

    public void setProperty(final String key, final Object value) {
        this.rawVertex.setProperty(key, value);
    }

    public Object removeProperty(final String key) {
        return this.rawVertex.removeProperty(key);
    }

    public Set<String> getPropertyKeys() {
        return this.rawVertex.getPropertyKeys();
    }

    public Iterable<Edge> getOutEdges(String... labels) {
        return this.graph.getDerivation(labels[0]).compute(this.rawVertex);
    }

    public Iterable<Edge> getInEdges(String... labels) {
        return this.graph.getDerivation(labels[0]).compute(this.rawVertex);
    }
}


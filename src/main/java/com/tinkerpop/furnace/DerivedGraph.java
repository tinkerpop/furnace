package com.tinkerpop.furnace;

import com.tinkerpop.blueprints.CloseableIterable;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Features;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedGraph implements Graph {

    private final Graph rawGraph;
    protected final Map<String, Derivation> derivations = new HashMap<String, Derivation>();


    public DerivedGraph(final Graph rawGraph) {
        this.rawGraph = rawGraph;
    }

    public void addDerivation(final String label, final Derivation derivation) {
        this.derivations.put(label, derivation);
    }

    public Derivation getDerivation(final String label) {
        return this.derivations.get(label);
    }

    public void shutdown() {
        this.rawGraph.shutdown();
    }

    public Iterable<Vertex> getVertices(final String key, final Object value) {
        return this.rawGraph.getVertices(key, value);
    }

    public Iterable<Edge> getEdges(final String key, final Object value) {
        return this.rawGraph.getEdges(key, value);
    }

    public Features getFeatures() {
        return this.rawGraph.getFeatures();
    }

    public Vertex addVertex(final Object id) {
        return new DerivedVertex(this.rawGraph.addVertex(id), this);
    }

    public void removeVertex(final Vertex vertex) {
        this.rawGraph.removeVertex(vertex);
    }

    public Vertex getVertex(final Object id) {
        return new DerivedVertex(this.rawGraph.getVertex(id), this);
    }

    public Iterable<Vertex> getVertices() {
        return this.rawGraph.getVertices();
    }

    public Iterable<Edge> getEdges() {
        return this.rawGraph.getEdges();
    }

    public Edge getEdge(final Object id) {
        return this.rawGraph.getEdge(id);
    }

    public Edge addEdge(final Object id, final Vertex outVertex, final Vertex inVertex, final String label) {
        return this.rawGraph.addEdge(id, outVertex, inVertex, label);
    }

    public void removeEdge(final Edge edge) {
        this.rawGraph.removeEdge(edge);
    }


}

package com.tinkerpop.furnace.wrappers;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Features;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.GraphQuery;
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

    @Override
    public void shutdown() {
        this.rawGraph.shutdown();
    }

    @Override
    public Iterable<Vertex> getVertices(final String key, final Object value) {
        return this.rawGraph.getVertices(key, value);
    }

    @Override
    public Iterable<Edge> getEdges(final String key, final Object value) {
        return this.rawGraph.getEdges(key, value);
    }

    @Override
    public Features getFeatures() {
        return this.rawGraph.getFeatures();
    }

    @Override
    public Vertex addVertex(final Object id) {
        return new DerivedVertex(this.rawGraph.addVertex(id), this);
    }

    @Override
    public void removeVertex(final Vertex vertex) {
        this.rawGraph.removeVertex(vertex);
    }

    @Override
    public Vertex getVertex(final Object id) {
        return new DerivedVertex(this.rawGraph.getVertex(id), this);
    }

    @Override
    public Iterable<Vertex> getVertices() {
        return this.rawGraph.getVertices();
    }

    @Override
    public Iterable<Edge> getEdges() {
        return this.rawGraph.getEdges();
    }

    @Override
    public Edge getEdge(final Object id) {
        return this.rawGraph.getEdge(id);
    }

    @Override
    public Edge addEdge(final Object id, final Vertex outVertex, final Vertex inVertex, final String label) {
        return this.rawGraph.addEdge(id, outVertex, inVertex, label);
    }

    @Override
    public void removeEdge(final Edge edge) {
        this.rawGraph.removeEdge(edge);
    }

    @Override
    public GraphQuery query() {
        return this.rawGraph.query();
    }

}

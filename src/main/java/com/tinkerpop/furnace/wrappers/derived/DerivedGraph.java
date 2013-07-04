package com.tinkerpop.furnace.wrappers.derived;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.GraphQuery;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.WrappedGraphQuery;
import com.tinkerpop.blueprints.util.wrappers.wrapped.WrappedGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedGraph<T extends Graph> extends WrappedGraph<T> implements Graph {

    protected final Map<String, Derivation> derivations = new HashMap<String, Derivation>();

    public DerivedGraph(final T baseGraph) {
        super(baseGraph);
    }

    public void addDerivation(final String label, final Derivation derivation) {
        this.derivations.put(label, derivation);
    }

    public Derivation getDerivation(final String label) {
        return this.derivations.get(label);
    }

    @Override
    public Iterable<Vertex> getVertices(final String key, final Object value) {
        return new DerivedVertexIterable(this.baseGraph.getVertices(key, value), this);
    }

    @Override
    public Iterable<Edge> getEdges(final String key, final Object value) {
        return new DerivedEdgeIterable(this.baseGraph.getEdges(key, value), this);
    }

    @Override
    public Vertex addVertex(final Object id) {
        return new DerivedVertex(this.baseGraph.addVertex(id), this);
    }

    @Override
    public Vertex getVertex(final Object id) {
        return new DerivedVertex(this.baseGraph.getVertex(id), this);
    }

    @Override
    public Iterable<Vertex> getVertices() {
        return new DerivedVertexIterable(this.baseGraph.getVertices(), this);
    }

    @Override
    public Iterable<Edge> getEdges() {
        return new DerivedEdgeIterable(this.baseGraph.getEdges(), this);
    }

    @Override
    public Edge getEdge(final Object id) {
        return new DerivedEdge(this.baseGraph.getEdge(id), this);
    }

    @Override
    public Edge addEdge(final Object id, final Vertex outVertex, final Vertex inVertex, final String label) {
        return new DerivedEdge(this.baseGraph.addEdge(id, outVertex, inVertex, label), this);
    }


    @Override
    public GraphQuery query() {
        final DerivedGraph g = this;
        return new WrappedGraphQuery(this.baseGraph.query()) {
            @Override
            public Iterable<Edge> edges() {
                return new DerivedEdgeIterable(this.query.edges(), g);
            }

            @Override
            public Iterable<Vertex> vertices() {
                return new DerivedVertexIterable(this.query.vertices(), g);
            }
        };
    }

}

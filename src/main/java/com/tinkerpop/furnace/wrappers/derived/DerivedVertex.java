package com.tinkerpop.furnace.wrappers.derived;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.blueprints.util.DefaultVertexQuery;
import com.tinkerpop.blueprints.util.MultiIterable;
import com.tinkerpop.blueprints.util.wrappers.WrapperVertexQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedVertex extends DerivedElement implements Vertex {

    public DerivedVertex(final Vertex rawVertex, final DerivedGraph derivedGraph) {
        super(rawVertex, derivedGraph);
    }

    @Override
    public Iterable<Edge> getEdges(final Direction direction, final String... labels) {
        final List<Iterable<Edge>> edges = new ArrayList<Iterable<Edge>>();
        for (final String label : labels) {
            final Derivation derivation = this.derivedGraph.getDerivation(label);
            if (null != derivation) {
                edges.add(derivation.incident(direction, this));
            } else {
                edges.add(new DerivedEdgeIterable(((Vertex) this.baseElement).getEdges(direction, label), this.derivedGraph));
            }
        }
        return new MultiIterable<Edge>(edges);
    }

    @Override
    public Iterable<Vertex> getVertices(final Direction direction, final String... labels) {
        final List<Iterable<Vertex>> vertices = new ArrayList<Iterable<Vertex>>();
        for (final String label : labels) {
            final Derivation derivation = this.derivedGraph.getDerivation(label);
            if (null != derivation) {
                vertices.add(derivation.adjacent(direction, this));
            } else {
                vertices.add(new DerivedVertexIterable(((Vertex) this.baseElement).getVertices(direction, label), this.derivedGraph));
            }
        }
        return new MultiIterable<Vertex>(vertices);
    }

    @Override
    public VertexQuery query() {
        return new WrapperVertexQuery(new DefaultVertexQuery(this)) {
            @Override
            public Iterable<Edge> edges() {
                return new DerivedEdgeIterable(this.query.edges(), derivedGraph);
            }

            @Override
            public Iterable<Vertex> vertices() {
                return new DerivedVertexIterable(this.query.vertices(), derivedGraph);
            }
        };
    }

    public Edge addEdge(final String label, final Vertex vertex) {
        if (vertex instanceof DerivedVertex)
            return new DerivedEdge(((Vertex) this.baseElement).addEdge(label, ((DerivedVertex) vertex).getBaseVertex()), this.derivedGraph);
        else
            return new DerivedEdge(((Vertex) this.baseElement).addEdge(label, vertex), this.derivedGraph);
    }

    public Vertex getBaseVertex() {
        return (Vertex) this.baseElement;
    }
}

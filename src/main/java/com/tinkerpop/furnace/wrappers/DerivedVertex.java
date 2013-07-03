package com.tinkerpop.furnace.wrappers;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.blueprints.util.MultiIterable;

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
                edges.add(((Vertex) this.rawElement).getEdges(direction, label));
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
                vertices.add(((Vertex) this.rawElement).getVertices(direction, label));
            }
        }
        return new MultiIterable<Vertex>(vertices);
    }

    @Override
    public VertexQuery query() {
        throw new UnsupportedOperationException();
    }


    @Override
    public Edge addEdge(String label, Vertex vertex) {
        return ((Vertex) this.rawElement).addEdge(label, ((Vertex) ((DerivedVertex) vertex).rawElement));
    }

}

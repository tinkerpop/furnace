package com.tinkerpop.furnace;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Query;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.DefaultQuery;
import com.tinkerpop.blueprints.util.MultiIterable;
import com.tinkerpop.blueprints.util.VerticesFromEdgesIterable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedVertex implements Vertex {

    private final DerivedGraph graph;
    protected final Vertex rawVertex;

    public DerivedVertex(final Vertex rawVertex, final DerivedGraph graph) {
        this.rawVertex = rawVertex;
        this.graph = graph;
    }

    public Iterable<Edge> getEdges(final Direction direction, final String... labels) {
        if (direction.equals(Direction.OUT)) {
            return this.getOutEdges(labels);
        } else if (direction.equals(Direction.IN))
            return this.getInEdges(labels);
        else {
            return new MultiIterable<Edge>(Arrays.asList(this.getInEdges(labels), this.getOutEdges(labels)));
        }
    }

    public Iterable<Vertex> getVertices(final Direction direction, final String... labels) {
        return new VerticesFromEdgesIterable(this, direction, labels);
    }

    private Iterable<Edge> getOutEdges(final String... labels) {
        List<Iterable<Edge>> iterables = new ArrayList<Iterable<Edge>>();
        for (final Object label : labels) {
            final Derivation derivation = this.graph.getDerivation((String) label);
            if (null != derivation)
                iterables.add(derivation.outEdges(this.rawVertex));
        }
        return new MultiIterable<Edge>(iterables);
    }

    private Iterable<Edge> getInEdges(final String... labels) {
        List<Iterable<Edge>> iterables = new ArrayList<Iterable<Edge>>();
        for (final Object label : labels) {
            final Derivation derivation = this.graph.getDerivation((String) label);
            if (null != derivation)
                iterables.add(derivation.inEdges(this.rawVertex));
        }
        return new MultiIterable<Edge>(iterables);
    }

    public Query query() {
        return new DefaultQuery(this);
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

    public int hashCode() {
        return this.rawVertex.hashCode();
    }

    public String toString() {
        return this.rawVertex.toString();
    }

    public boolean equals(Object object) {
        return (object instanceof Vertex) && ((Vertex) object).getId().equals(this.getId());
    }

}


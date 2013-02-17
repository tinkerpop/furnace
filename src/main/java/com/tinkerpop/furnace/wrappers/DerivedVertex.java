package com.tinkerpop.furnace.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Query;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.DefaultQuery;
import com.tinkerpop.blueprints.util.MultiIterable;

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

	@Override
	public Iterable<Edge> getEdges(final Direction direction, final String... labels) {
		return new ArrayList<Edge>();
	}

	@Override
	public Iterable<Vertex> getVertices(final Direction direction, final String... labels) {
		final List<Iterable<Vertex>> vertices = new ArrayList<Iterable<Vertex>>();
		for (final String label : labels) {
			final Derivation derivation = this.graph.getDerivation(label);
			if (null != derivation) {
				vertices.add(derivation.adjacent(this));
			} else {
				vertices.add(this.rawVertex.getVertices(direction, label));
			}
		}
		return new MultiIterable<Vertex>(vertices);
	}

	@Override
	public Query query() {
		return new DefaultQuery(this);
	}

	@Override
	public Object getId() {
		return this.rawVertex.getId();
	}

	@Override
	public Object getProperty(final String key) {
		return this.rawVertex.getProperty(key);
	}

	@Override
	public void setProperty(final String key, final Object value) {
		this.rawVertex.setProperty(key, value);
	}

	@Override
	public Object removeProperty(final String key) {
		return this.rawVertex.removeProperty(key);
	}

	@Override
	public Set<String> getPropertyKeys() {
		return this.rawVertex.getPropertyKeys();
	}

	@Override
	public int hashCode() {
		return this.rawVertex.hashCode();
	}

	@Override
	public String toString() {
		return this.rawVertex.toString();
	}

	@Override
	public boolean equals(Object object) {
		return (object instanceof Vertex) && ((Vertex) object).getId().equals(this.getId());
	}

	@Override
	public void remove() {
		this.rawVertex.remove();
	}

	@Override
	public Edge addEdge(String label, Vertex vertex) {
		return this.rawVertex.addEdge(label, vertex);
	}

}

package com.tinkerpop.furnace.algorithms;

import java.util.Map;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nicholas A. Stuart (nicholasastuart@gmail.com)
 */
public abstract class SingleSourceShortestPathAlgorithm {
	private final Graph graph;

	public SingleSourceShortestPathAlgorithm(final Graph graph) {
		this.graph = graph;
	}

	/**
	 * 
	 * @param source
	 *            Source node from which to find the shortest path from.
	 * @param weightPropertyName
	 *            Name of the property on each edge to use for weight. Cannot be {@code null} or empty.
	 * @param weightedEdgeLabel
	 *            Edge label to go out from the vertex. {@code null} or empty is allowed and will use any edge label to traverse.
	 * @return {@code Map<Vertex, Long>} with key of {@code Vertex} in the graph and value of distance to {@code Vertex}.
	 */
	public abstract Map<Vertex, Long> compute(final Vertex source, final String weightPropertyName, final String... weightedEdgeLabels);

	public final Graph getGraph() {
		return graph;
	}
}
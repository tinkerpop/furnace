package com.tinkerpop.furnace.algorithms.graphcentric.shortestpath;

import java.util.List;
import java.util.Map;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nicholas A. Stuart (nicholasastuart@gmail.com)
 */
public abstract class SingleSourceShortestPathAlgorithm {
	protected final Graph graph;

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
	 *            Edge label to go out from the vertex. If none are specified, all edge labels will be considered.
	 * @return {@code Map<Vertex, List<Edge>} with key of {@code Vertex} in the graph and value is a list of the ordered edges composing the shortest path.
	 */
	public abstract Map<Vertex, List<Edge>> compute(final Vertex source, final String weightPropertyName, final String... weightedEdgeLabels);

	public final Graph getGraph() {
		return graph;
	}
}
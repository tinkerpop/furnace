package com.tinkerpop.furnace.algorithms.search;

import java.util.List;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public abstract class SearchAlgorithm {
	protected final Graph graph;

	public SearchAlgorithm(Graph graph) {
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	public abstract List<Edge> findPathToTarget(Vertex start, Vertex end);
}
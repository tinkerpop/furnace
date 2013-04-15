package com.tinkerpop.furnace.util;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public interface HeuristicAlgorithm {
	public Long costEstimation(Graph graph, Vertex start, Vertex goal);
}
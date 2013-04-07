package com.tinkerpop.furnace.algorithms.search;

import java.util.List;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public interface SearchAlgorithm {
	public List<Edge> findPathToTarget(Vertex start, Vertex end);
}
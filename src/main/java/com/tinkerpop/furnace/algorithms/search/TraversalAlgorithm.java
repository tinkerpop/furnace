package com.tinkerpop.furnace.algorithms.search;

import java.util.List;

import com.tinkerpop.blueprints.Vertex;

public interface TraversalAlgorithm {
	public List<Vertex> traverseTree(Vertex start);
}
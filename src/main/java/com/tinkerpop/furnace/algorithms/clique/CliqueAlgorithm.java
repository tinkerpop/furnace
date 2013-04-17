package com.tinkerpop.furnace.algorithms.clique;

import java.util.Set;

import com.tinkerpop.blueprints.Vertex;

public interface CliqueAlgorithm {
	public Set<Set<Vertex>> findAllMaximalCliques();
}
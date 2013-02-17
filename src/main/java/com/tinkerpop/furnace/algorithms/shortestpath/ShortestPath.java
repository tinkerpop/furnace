package com.tinkerpop.furnace.algorithms.shortestpath;

import java.util.ArrayList;
import java.util.List;

import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ShortestPath {

	private final Graph graph;

	public ShortestPath(final Graph graph) {
		this.graph = graph;
	}

	public List<Element> compute(final Vertex start, final Vertex end, final String... labels) {
		return new ArrayList<Element>();
	}

	public Graph getGraph() {
		return graph;
	}
}
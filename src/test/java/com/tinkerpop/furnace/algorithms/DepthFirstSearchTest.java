package com.tinkerpop.furnace.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.furnace.algorithms.search.DepthFirstSearch;
import com.tinkerpop.furnace.algorithms.search.SearchAlgorithm;

public class DepthFirstSearchTest {

	@Test
	public void bfsSimpleTest() {
		Graph graph = new TinkerGraph();
		Vertex vertex1 = graph.addVertex(null);
		Vertex vertex2 = graph.addVertex(null);
		Vertex vertex3 = graph.addVertex(null);
		Vertex vertex4 = graph.addVertex(null);

		Edge edge1 = vertex1.addEdge("TO", vertex2);
		Edge edge2 = vertex2.addEdge("TO", vertex3);
		Edge edge3 = vertex1.addEdge("TO", vertex4);

		SearchAlgorithm dfs = new DepthFirstSearch(graph);
		List<Edge> path1 = dfs.findPathToTarget(vertex1, vertex3);
		List<Edge> path2 = dfs.findPathToTarget(vertex1, vertex4);
		assertEquals(path1, Arrays.asList(edge1, edge2));
		assertEquals(path2, Collections.singletonList(edge3));
	}
}
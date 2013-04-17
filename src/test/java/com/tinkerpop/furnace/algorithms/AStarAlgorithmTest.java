package com.tinkerpop.furnace.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.furnace.algorithms.search.AStarAlgorithm;
import com.tinkerpop.furnace.algorithms.search.SearchAlgorithm;

public class AStarAlgorithmTest {
	private Graph graph;
	private Vertex vertex1;
	private Vertex vertex2;
	private Vertex vertex3;
	private Vertex vertex4;
	private Edge edge1;
	private Edge edge2;
	private Edge edge3;

	@Before
	public void setUp() {
		graph = new TinkerGraph();
		vertex1 = graph.addVertex(null);
		vertex2 = graph.addVertex(null);
		vertex3 = graph.addVertex(null);
		vertex4 = graph.addVertex(null);

		edge1 = vertex1.addEdge("TO", vertex2);
		edge1.setProperty("weight", 1L);

		edge2 = vertex2.addEdge("TO", vertex3);
		edge2.setProperty("weight", 1L);

		edge3 = vertex1.addEdge("TO", vertex4);
		edge3.setProperty("weight", 1L);
	}

	@Test
	public void aStarSimpleTest() {
		SearchAlgorithm bfs = new AStarAlgorithm(graph, null);
		List<Edge> path1 = bfs.findPathToTarget(vertex1, vertex3);
		assertEquals(path1, Arrays.asList(edge1, edge2));

		List<Edge> path2 = bfs.findPathToTarget(vertex1, vertex4);
		assertEquals(path2, Collections.singletonList(edge3));
	}
}
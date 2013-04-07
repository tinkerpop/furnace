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
import com.tinkerpop.furnace.algorithms.search.BreadthFirstAlgorithm;
import com.tinkerpop.furnace.algorithms.search.SearchAlgorithm;
import com.tinkerpop.furnace.algorithms.search.TraversalAlgorithm;

public class BreadthFirstSearchTest {
	private Graph graph;;
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
		edge2 = vertex2.addEdge("TO", vertex3);
		edge3 = vertex1.addEdge("TO", vertex4);
	}

	@Test
	public void bfaSearchSimpleTest() {
		SearchAlgorithm bfs = new BreadthFirstAlgorithm(graph);
		List<Edge> path1 = bfs.findPathToTarget(vertex1, vertex3);
		List<Edge> path2 = bfs.findPathToTarget(vertex1, vertex4);
		assertEquals(path1, Arrays.asList(edge1, edge2));
		assertEquals(path2, Collections.singletonList(edge3));
	}

	@Test
	public void bfaTraversalSimpleTest() {
		TraversalAlgorithm bfs = new BreadthFirstAlgorithm(graph);
		List<Vertex> traversalList = bfs.traverseTree(vertex1);

		assertEquals(traversalList, Arrays.asList(vertex1, vertex4, vertex2, vertex3));
	}
}
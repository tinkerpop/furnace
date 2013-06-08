package com.tinkerpop.furnace.algorithms.graphcentric;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.furnace.algorithms.graphcentric.shortestpath.BellmanFordAlgorithm;
import com.tinkerpop.furnace.algorithms.graphcentric.shortestpath.SingleSourceShortestPathAlgorithm;

public class BellmanFordAlgorithmTest {

	@Test
	public void simpleTest() {
		Graph graph = new TinkerGraph();
		Vertex vertex1 = graph.addVertex(null);
		Vertex vertex2 = graph.addVertex(null);
		Vertex vertex3 = graph.addVertex(null);

		Edge edge1 = vertex1.addEdge("TO", vertex2);
		Edge edge2 = vertex2.addEdge("TO", vertex3);

		edge1.setProperty("weight", 1L);
		edge2.setProperty("weight", 1L);

		SingleSourceShortestPathAlgorithm ssspa = new BellmanFordAlgorithm(graph);
		Map<Vertex, List<Edge>> shortMap = ssspa.compute(vertex1, "weight");
		assertEquals(shortMap.get(vertex1), null);
		assertEquals(shortMap.get(vertex2), Collections.singletonList(edge1));
		assertTrue(shortMap.get(vertex3).containsAll(Arrays.asList(edge1, edge2)));
	}

	@Test
	public void negativeWeightTest() {
		Graph graph = new TinkerGraph();
		Vertex vertex1 = graph.addVertex(null);
		Vertex vertex2 = graph.addVertex(null);
		Vertex vertex3 = graph.addVertex(null);

		Edge edge1 = vertex1.addEdge("TO", vertex2);
		Edge edge2 = vertex2.addEdge("TO", vertex3);

		edge1.setProperty("weight", -1L);
		edge2.setProperty("weight", 1L);

		SingleSourceShortestPathAlgorithm ssspa = new BellmanFordAlgorithm(graph);
		Map<Vertex, List<Edge>> shortMap = ssspa.compute(vertex1, "weight");
		assertEquals(shortMap.get(vertex1), null);
		assertEquals(shortMap.get(vertex2), Collections.singletonList(edge1));
		assertTrue(shortMap.get(vertex3).containsAll(Arrays.asList(edge1, edge2)));
	}

	@Test
	public void sinkTest() {
		Graph graph = new TinkerGraph();
		Vertex vertex1 = graph.addVertex(null);
		Vertex vertex2 = graph.addVertex(null);
		Vertex vertex3 = graph.addVertex(null);

		Edge edge1 = vertex2.addEdge("TO", vertex1);
		Edge edge2 = vertex3.addEdge("TO", vertex1);

		edge1.setProperty("weight", 1L);
		edge2.setProperty("weight", 1L);

		SingleSourceShortestPathAlgorithm ssspa = new BellmanFordAlgorithm(graph);
		Map<Vertex, List<Edge>> shortMap = ssspa.compute(vertex1, "weight");
		assertEquals(shortMap.get(vertex1), null);
		assertEquals(shortMap.get(vertex2), null);
		assertEquals(shortMap.get(vertex3), null);
	}
}

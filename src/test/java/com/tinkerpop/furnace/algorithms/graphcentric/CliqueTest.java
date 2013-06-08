package com.tinkerpop.furnace.algorithms.graphcentric;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.furnace.algorithms.graphcentric.clique.BronKerboschAlgorithm;
import com.tinkerpop.furnace.algorithms.graphcentric.clique.CliqueAlgorithm;

public class CliqueTest extends TestCase {
	private CliqueAlgorithm cliqueAlgorithm;
	private Vertex vertex1;
	private Vertex vertex2;
	private Vertex vertex3;
	private Vertex vertex4;
	private Vertex vertex5;
	private Vertex vertex6;

	@Before
	public void setUp() {
		Graph g = new TinkerGraph();

		vertex1 = g.addVertex(1);
		vertex2 = g.addVertex(2);
		vertex3 = g.addVertex(3);
		vertex4 = g.addVertex(4);
		vertex5 = g.addVertex(5);
		vertex6 = g.addVertex(6);

		makeBidirectionalRelationship(vertex1, vertex5, "link");
		makeBidirectionalRelationship(vertex1, vertex2, "link");
		makeBidirectionalRelationship(vertex5, vertex2, "link");
		makeBidirectionalRelationship(vertex3, vertex2, "link");
		makeBidirectionalRelationship(vertex4, vertex5, "link");
		makeBidirectionalRelationship(vertex6, vertex4, "link");
		makeBidirectionalRelationship(vertex3, vertex4, "link");

		cliqueAlgorithm = new BronKerboschAlgorithm(g);
	}

	@Test
	public void testAllMaximalCliques() {
		Set<Set<Vertex>> cliques = cliqueAlgorithm.findAllMaximalCliques();
		assertTrue(cliques.contains(new HashSet<Vertex>(Arrays.asList(vertex2, vertex1, vertex5))));
		assertTrue(cliques.contains(new HashSet<Vertex>(Arrays.asList(vertex3, vertex4))));
		assertTrue(cliques.contains(new HashSet<Vertex>(Arrays.asList(vertex2, vertex3))));
		assertTrue(cliques.contains(new HashSet<Vertex>(Arrays.asList(vertex4, vertex5))));
		assertTrue(cliques.contains(new HashSet<Vertex>(Arrays.asList(vertex4, vertex6))));

		assertFalse(cliques.contains(new HashSet<Vertex>(Arrays.asList(vertex2, vertex1))));
	}

	private void makeBidirectionalRelationship(Vertex firstVertex, Vertex secondVertex, String label) {
		firstVertex.addEdge(label, secondVertex);
		secondVertex.addEdge(label, firstVertex);
	}
}
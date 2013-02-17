package com.tinkerpop.furnace.wrappers;

import static com.tinkerpop.blueprints.Direction.OUT;
import junit.framework.TestCase;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.gremlin.java.GremlinPipeline;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedGraphTest extends TestCase {

	public void testTrue() {
		Graph graph = TinkerGraphFactory.createTinkerGraph();
		final DerivedGraph derived = new DerivedGraph(graph);
		derived.addDerivation("coworker", new Derivation() {
			public Iterable<Vertex> adjacent(final Vertex vertex) {
				return new GremlinPipeline<Vertex, Vertex>(vertex).out("created").in("created");
			}

		});
		int counter = 0;
		for (Vertex vertex : derived.getVertex(1).getVertices(OUT, "coworker")) {
			assertTrue(vertex.getId().equals("1") || vertex.getId().equals("6") || vertex.getId().equals("4"));
			counter++;
		}

		assertEquals(counter, 3);

	}
}

package com.tinkerpop.furnace;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.util.DerivedEdgeSequence;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedGraphTest extends TestCase {

    public void testTrue() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        final DerivedGraph derived = new DerivedGraph(graph);
        derived.addDerivation("coworker", new Derivation() {
            public Iterable<Edge> outEdges(Vertex vertex) {
                return new DerivedEdgeSequence(derived, vertex, new GremlinPipeline(vertex).out("created").in("created"), "coworker");
            }

            public Iterable<Edge> inEdges(Vertex vertex) {
                return new DerivedEdgeSequence(derived, vertex, new GremlinPipeline(vertex).out("created").in("created"), "coworker");
            }
        });
        int counter = 0;
        for (Edge edge : derived.getVertex(1).getOutEdges("coworker")) {
            assertEquals(edge.getOutVertex(), derived.getVertex(1));
            assertTrue(edge.getInVertex().equals(derived.getVertex(1)) ||
                    edge.getInVertex().equals(derived.getVertex(4)) ||
                    edge.getInVertex().equals(derived.getVertex(6)));
            counter++;
        }

        assertEquals(counter, 3);

    }
}

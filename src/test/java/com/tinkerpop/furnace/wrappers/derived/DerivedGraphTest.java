package com.tinkerpop.furnace.wrappers.derived;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.PipesFunction;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedGraphTest extends TestCase {

    static Graph graph = TinkerGraphFactory.createTinkerGraph();
    static DerivedGraph derivedGraph = new DerivedGraph<Graph>(graph);

    static {
        derivedGraph.addDerivation("codeveloper", new Derivation() {
            @Override
            public Iterable<Vertex> adjacent(final Direction direction, final Vertex vertex) {
                return new GremlinPipeline<Vertex, Vertex>(vertex).as("x").out("created").in("created").except("x");
            }

            @Override
            public Iterable<Edge> incident(final Direction direction, final Vertex vertex) {
                return new GremlinPipeline<Vertex, Edge>(vertex).as("x").out("created").in("created").except("x").transform(new PipesFunction<Vertex, Edge>() {
                    @Override
                    public Edge compute(Vertex argument) {
                        return DerivedEdge.generateDerivedEdge(vertex, argument, "codeveloper", derivedGraph);
                    }
                });
            }
        });
    }

    public void testCoDeveloperGetVertices() {
        Vertex marko = derivedGraph.getVertex(1);
        int counter = 0;
        for (Vertex vertex : marko.getVertices(Direction.OUT, "codeveloper")) {
            counter++;
            assertTrue(vertex.getProperty("name").equals("josh") || vertex.getProperty("name").equals("peter"));
        }
        assertEquals(counter, 2);

        counter = 0;
        for (Vertex vertex : (Iterable<Vertex>) derivedGraph.getVertices()) {
            for (Vertex vertex2 : vertex.getVertices(Direction.OUT, "codeveloper")) {
                counter++;
            }
        }
        assertEquals(counter, 6);
    }

    public void testCoDeveloperVertexQuery() {
        int counter = 0;

        for (Vertex vertex : (Iterable<Vertex>) derivedGraph.getVertices()) {
            for (Vertex vertex2 : vertex.query().labels("codeveloper").vertices()) {
                counter++;
            }
        }
        assertEquals(counter, 6);
    }

}

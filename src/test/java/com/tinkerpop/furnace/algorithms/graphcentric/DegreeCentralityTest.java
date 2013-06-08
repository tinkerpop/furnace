package com.tinkerpop.furnace.algorithms.graphcentric;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.algorithms.graphcentric.rank.DegreeCentrality;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import junit.framework.TestCase;

import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DegreeCentralityTest extends TestCase {

    public void testCompute() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        DegreeCentrality dc = new DegreeCentrality();
        Map<Vertex, Long> rank = dc.compute(graph, new VertexQueryBuilder().labels("created").direction(Direction.OUT));
        assertEquals(rank.get(graph.getVertex(1)), new Long(1));
        assertEquals(rank.get(graph.getVertex(2)), new Long(0));
        assertEquals(rank.get(graph.getVertex(3)), new Long(0));
        assertEquals(rank.get(graph.getVertex(4)), new Long(2));
        assertEquals(rank.get(graph.getVertex(5)), new Long(0));
        assertEquals(rank.get(graph.getVertex(6)), new Long(1));


    }
}

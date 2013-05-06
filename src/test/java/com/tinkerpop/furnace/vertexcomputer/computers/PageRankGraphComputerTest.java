package com.tinkerpop.furnace.vertexcomputer.computers;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import com.tinkerpop.furnace.vertexcompute.ComputeResult;
import com.tinkerpop.furnace.vertexcompute.GraphComputer;
import com.tinkerpop.furnace.vertexcompute.VertexComputerGraph;
import com.tinkerpop.furnace.vertexcompute.computers.PageRankGraphComputer;
import com.tinkerpop.furnace.vertexcompute.util.SimpleSharedState;
import com.tinkerpop.furnace.vertexcompute.wrappers.SerialVertexComputerGraph;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankGraphComputerTest extends TestCase {

    public void testGraphComputer() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        VertexComputerGraph vertexComputerGraph = new SerialVertexComputerGraph<Graph>(graph);
        GraphComputer graphComputer = PageRankGraphComputer.create()
                .prefix("a.test.prefix").alpha(0.85d)
                .iterations(30).vertexCount(6)
                .query(new VertexQueryBuilder().direction(Direction.OUT))
                .sharedState(new SimpleSharedState()).build();

        vertexComputerGraph.execute(graphComputer);
        ComputeResult result = graphComputer.generateResult();

        double total = 0.0d;
        for (Vertex vertex : graph.getVertices()) {
            double pageRank = result.getResult(vertex);
            total = total + pageRank;
            System.out.println(vertex + " " + pageRank);
            assertTrue(pageRank > 0.0d);
        }
        System.out.println(total);

        /*for (int i = 1; i < 7; i++) {
            double pageRank = result.getResult(graph.getVertex(i));
            System.out.println(i + " " + (pageRank / total));
        }*/

        vertexComputerGraph.cleanup(graphComputer);
        for (Vertex vertex : graph.getVertices()) {
            assertNull(result.getResult(vertex));
        }
    }
}

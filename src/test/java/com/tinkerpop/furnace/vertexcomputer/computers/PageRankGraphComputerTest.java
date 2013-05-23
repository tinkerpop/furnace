package com.tinkerpop.furnace.vertexcomputer.computers;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import com.tinkerpop.furnace.vertexcomputer.ComputerProperties;
import com.tinkerpop.furnace.vertexcomputer.GraphComputer;
import com.tinkerpop.furnace.vertexcomputer.VertexComputerGraph;
import com.tinkerpop.furnace.vertexcomputer.util.SimpleSharedState;
import com.tinkerpop.furnace.vertexcomputer.wrappers.MapComputerProperties;
import com.tinkerpop.furnace.vertexcomputer.wrappers.SerialVertexComputerGraph;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankGraphComputerTest extends TestCase {

    public void testGraphComputer() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        VertexComputerGraph vertexComputerGraph = new SerialVertexComputerGraph<Graph>(graph);
        GraphComputer graphComputer = PageRankGraphComputer.create()
                .alpha(0.85d).iterations(30).vertexCount(6)
                .outgoing(new VertexQueryBuilder().direction(Direction.OUT))
                .incoming(new VertexQueryBuilder().direction(Direction.IN))
                .computeKeys("pageRank", "edgeCount")
                .sharedState(new SimpleSharedState())
                .computerProperties(new MapComputerProperties(new HashMap<Object, Map<String, Object>>())).build();

        ComputerProperties results = vertexComputerGraph.execute(graphComputer);

        double total = 0.0d;
        for (Vertex vertex : graph.getVertices()) {
            double pageRank = results.getProperty(vertex, PageRankGraphComputer.PAGE_RANK);
            total = total + pageRank;
            System.out.println(vertex + " " + pageRank);
            assertTrue(pageRank > 0.0d);
        }
        System.out.println(total);

        /*for (int i = 1; i < 7; i++) {
            double pageRank = result.getResult(graph.getVertex(i));
            System.out.println(i + " " + (pageRank / total));
        }*/


    }
}

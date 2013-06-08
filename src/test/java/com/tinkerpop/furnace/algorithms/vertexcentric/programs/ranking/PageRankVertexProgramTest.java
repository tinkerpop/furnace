package com.tinkerpop.furnace.algorithms.vertexcentric.programs.ranking;

import com.google.common.collect.ImmutableSortedMap;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphComputer;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.computers.SerialGraphComputer;
import junit.framework.TestCase;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankVertexProgramTest extends TestCase {

    public void testGraphComputer() throws Exception {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        //Graph graph = new TinkerGraph();
        //GraphMLReader.inputGraph(graph, "/Users/marko/software/tinkerpop/gremlin/data/graph-example-2.xml");

        PageRankVertexProgram program = PageRankVertexProgram.create().vertexCount(6).build();
        SerialGraphComputer computer = new SerialGraphComputer(graph, program, GraphComputer.Isolation.BSP);
        computer.execute();

        VertexMemory results = computer.getVertexMemory();

        System.out.println(results);

        double total = 0.0d;
        final Map<String, Double> map = new HashMap<String, Double>();
        for (Vertex vertex : graph.getVertices()) {
            double pageRank = results.getProperty(vertex, PageRankVertexProgram.PAGE_RANK);
            assertTrue(pageRank > 0.0d);
            total = total + pageRank;
            map.put(vertex.getProperty("name") + " ", pageRank);
        }
        for (Map.Entry<String, Double> entry : ImmutableSortedMap.copyOf(map, new Comparator<String>() {
            public int compare(final String key, final String key2) {
                int c = map.get(key2).compareTo(map.get(key));
                return c == 0 ? -1 : c;
            }
        }).entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println(total);
        System.out.println(computer.getGraphMemory().getRuntime());

        /*for (int i = 1; i < 7; i++) {
            double PAGE_RANK = result.getResult(graph.getVertex(i));
            System.out.println(i + " " + (PAGE_RANK / total));
        }*/


    }
}

package com.tinkerpop.furnace.computer.programs.ranking;

import com.google.common.collect.ImmutableSortedMap;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.computer.GraphComputer;
import com.tinkerpop.furnace.computer.LocalMemory;
import com.tinkerpop.furnace.computer.evaluators.SerialEvaluator;
import com.tinkerpop.furnace.computer.memory.SimpleGlobalMemory;
import com.tinkerpop.furnace.computer.memory.SimpleLocalMemory;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import junit.framework.TestCase;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankGraphComputerTest extends TestCase {

    public void testGraphComputer() throws Exception {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        //Graph graph = new TinkerGraph();
        //GraphMLReader.inputGraph(graph, "/Users/marko/software/tinkerpop/gremlin/data/graph-example-2.xml");

        GraphComputer graphComputer = PageRankGraphComputer.create()
                .graph(graph).alpha(0.85d).iterations(10).vertexCount(6)
                .outgoing(new VertexQueryBuilder().direction(Direction.OUT))
                .incoming(new VertexQueryBuilder().direction(Direction.IN))
                        //.isolation(Isolation.DIRTY_BSP)
                .evaluator(new SerialEvaluator())
                .globalMemory(new SimpleGlobalMemory())
                .localMemory(new SimpleLocalMemory()).build();
        graphComputer.execute();

        LocalMemory results = graphComputer.getLocalMemory();

        System.out.println(results);

        double total = 0.0d;
        final Map<String, Double> map = new HashMap<String, Double>();
        for (Vertex vertex : graph.getVertices()) {
            double pageRank = results.getProperty(vertex, PageRankGraphComputer.PAGE_RANK);
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

        /*for (int i = 1; i < 7; i++) {
            double pageRank = result.getResult(graph.getVertex(i));
            System.out.println(i + " " + (pageRank / total));
        }*/


    }
}

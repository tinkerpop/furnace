package com.tinkerpop.furnace.computer.programs.clustering;

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
public class PeerPressureGraphComputerTest extends TestCase {

    public void testGraphComputer() throws Exception {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        //Graph graph = new TinkerGraph();
        //GraphMLReader.inputGraph(graph, "/Users/marko/software/tinkerpop/gremlin/data/graph-example-2.xml");

        GraphComputer graphComputer = PeerPressureGraphComputer.create()
                .graph(graph).iterations(30)
                .outgoingQuery(new VertexQueryBuilder().direction(Direction.OUT))
                .incomingQuery(new VertexQueryBuilder().direction(Direction.IN))
                .evaluator(new SerialEvaluator())
                .globalMemory(new SimpleGlobalMemory())
                .localMemory(new SimpleLocalMemory()).build();
        graphComputer.execute();

        LocalMemory results = graphComputer.getLocalMemory();

        System.out.println(results);

        final Map<String, Object> map = new HashMap<String, Object>();
        for (Vertex vertex : graph.getVertices()) {
            Object cluster = results.getProperty(vertex, PeerPressureGraphComputer.CLUSTER);
            map.put(vertex.getProperty("name") + " ", cluster);
        }
        for (Map.Entry<String, Object> entry : ImmutableSortedMap.copyOf(map, new Comparator<String>() {
            public int compare(final String key, final String key2) {
                int c = ((String) map.get(key2)).compareTo((String) map.get(key));
                return c == 0 ? -1 : c;
            }
        }).entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        /*for (int i = 1; i < 7; i++) {
            double pageRank = result.getResult(graph.getVertex(i));
            System.out.println(i + " " + (pageRank / total));
        }*/


    }
}
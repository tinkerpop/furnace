package com.tinkerpop.furnace.algorithms.vertexcentric.programs.clustering;

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
public class PeerPressureVertexProgramTest extends TestCase {

    public void testProgramOnTinkerGraph() throws Exception {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        //Graph graph = new TinkerGraph();
        //GraphMLReader.inputGraph(graph, "/Users/marko/software/tinkerpop/gremlin/data/graph-example-2.xml");

        PeerPressureVertexProgram program = PeerPressureVertexProgram.create().build();
        SerialGraphComputer computer = new SerialGraphComputer(graph, program, GraphComputer.Isolation.BSP);
        computer.execute();

        VertexMemory results = computer.getVertexMemory();

        System.out.println(results);

        final Map<String, Object> map = new HashMap<String, Object>();
        for (Vertex vertex : graph.getVertices()) {
            Object cluster = results.getProperty(vertex, PeerPressureVertexProgram.CLUSTER);
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
            double PAGE_RANK = result.getResult(graph.getVertex(i));
            System.out.println(i + " " + (PAGE_RANK / total));
        }*/


    }
}
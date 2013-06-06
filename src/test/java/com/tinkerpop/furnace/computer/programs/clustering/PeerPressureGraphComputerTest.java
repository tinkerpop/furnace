package com.tinkerpop.furnace.computer.programs.clustering;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PeerPressureGraphComputerTest extends TestCase {

    public void testGraphComputer() throws Exception {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        //Graph graph = new TinkerGraph();
        //GraphMLReader.inputGraph(graph, "/Users/marko/software/tinkerpop/gremlin/data/graph-example-2.xml");

       /* GraphComputer graphComputer = PeerPressureGraphComputer.create()
                .graph(graph).iterations(30)
                .outgoingQuery(new VertexQueryBuilder().direction(Direction.OUT))
                .incomingQuery(new VertexQueryBuilder().direction(Direction.IN))
                .evaluator(new SerialGraphComputer())
                .globalMemory(new SimpleGraphMemory())
                .localMemory(new SimpleVertexMemory()).build();
        graphComputer.execute();

        VertexMemory results = graphComputer.getVertexMemory();

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
            double PAGE_RANK = result.getResult(graph.getVertex(i));
            System.out.println(i + " " + (PAGE_RANK / total));
        }*/


    }
}
package com.tinkerpop.furnace.computer.programs.paths;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ShortestPathGraphComputerTest extends TestCase {

    public void testGraphComputer() throws Exception {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        //Graph graph = new TinkerGraph();
        //GraphMLReader.inputGraph(graph, "/Users/marko/software/tinkerpop/gremlin/data/graph-example-2.xml");

        /*GraphComputer graphComputer = ShortestPathGraphComputer.create()
                .graph(graph).iterations(10)
                .incoming(new VertexQueryBuilder().direction(Direction.IN))
                        //.isolation(Isolation.DIRTY_BSP)
                .evaluator(new SerialGraphComputer())
                .globalMemory(new SimpleGraphMemory())
                .localMemory(new SimpleVertexMemory()).build();
        graphComputer.execute();

        VertexMemory results = graphComputer.getVertexMemory();

        System.out.println(results);*/
    }
}

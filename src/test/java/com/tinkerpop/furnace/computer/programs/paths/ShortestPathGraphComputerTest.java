package com.tinkerpop.furnace.computer.programs.paths;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.computer.GraphComputer;
import com.tinkerpop.furnace.computer.LocalMemory;
import com.tinkerpop.furnace.computer.evaluators.SerialEvaluator;
import com.tinkerpop.furnace.computer.memory.SimpleGlobalMemory;
import com.tinkerpop.furnace.computer.memory.SimpleLocalMemory;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ShortestPathGraphComputerTest extends TestCase {

    public void testGraphComputer() throws Exception {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        //Graph graph = new TinkerGraph();
        //GraphMLReader.inputGraph(graph, "/Users/marko/software/tinkerpop/gremlin/data/graph-example-2.xml");

        GraphComputer graphComputer = ShortestPathGraphComputer.create()
                .graph(graph).iterations(10)
                .incoming(new VertexQueryBuilder().direction(Direction.IN))
                        //.isolation(Isolation.DIRTY_BSP)
                .evaluator(new SerialEvaluator())
                .globalMemory(new SimpleGlobalMemory())
                .localMemory(new SimpleLocalMemory()).build();
        graphComputer.execute();

        LocalMemory results = graphComputer.getLocalMemory();

        System.out.println(results);
    }
}

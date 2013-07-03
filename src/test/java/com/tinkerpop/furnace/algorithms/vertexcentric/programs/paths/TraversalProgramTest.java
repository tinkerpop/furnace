package com.tinkerpop.furnace.algorithms.vertexcentric.programs.paths;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphComputer;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.computers.SerialGraphComputer;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class TraversalProgramTest extends TestCase {

    public void testTraversalProgram() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();

        TraversalProgram program = TraversalProgram.create().iterations(1)
                .addQuery(new VertexQueryBuilder().direction(Direction.IN).labels("created"))
                .addQuery(new VertexQueryBuilder().direction(Direction.OUT).labels("created")).build();
        SerialGraphComputer computer = new SerialGraphComputer(graph, program, GraphComputer.Isolation.BSP);
        computer.execute();

        VertexMemory results = computer.getVertexMemory();
        for (Vertex vertex : graph.getVertices()) {
            System.out.println(vertex.getProperty("name") + "\t" + results.getProperty(vertex, TraversalProgram.COUNTS));
        }
    }
}

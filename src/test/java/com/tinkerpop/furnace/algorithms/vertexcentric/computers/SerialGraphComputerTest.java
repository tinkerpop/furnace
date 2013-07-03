package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphComputer;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.programs.ranking.DegreeRankProgram;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SerialGraphComputerTest extends TestCase {

    public void testBasicSerialComputer() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        DegreeRankProgram program = DegreeRankProgram.create().build();
        SerialGraphComputer computer = new SerialGraphComputer(graph, program, GraphComputer.Isolation.BSP);
        computer.execute();
        VertexMemory results = computer.getVertexMemory();
        assertEquals(results.getProperty(graph.getVertex(1), DegreeRankProgram.DEGREE), 0l);
        assertEquals(results.getProperty(graph.getVertex(2), DegreeRankProgram.DEGREE), 1l);
        assertEquals(results.getProperty(graph.getVertex(3), DegreeRankProgram.DEGREE), 3l);
        assertEquals(results.getProperty(graph.getVertex(4), DegreeRankProgram.DEGREE), 1l);
        assertEquals(results.getProperty(graph.getVertex(5), DegreeRankProgram.DEGREE), 1l);
        assertEquals(results.getProperty(graph.getVertex(6), DegreeRankProgram.DEGREE), 0l);

    }
}

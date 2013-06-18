package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphComputer;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.programs.ranking.DegreeRankVertexProgram;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ParallelGraphComputerTest extends TestCase {

    public void testBasicParallelComputer() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();

        for (int workers = 1; workers < 25; workers++) {
            for (int threads = 1; threads < 25; threads++) {
                DegreeRankVertexProgram program = DegreeRankVertexProgram.create().build();
                ParallelGraphComputer computer = new ParallelGraphComputer(graph, program, GraphComputer.Isolation.BSP);
                computer.execute();
                VertexMemory results = computer.getVertexMemory();
                testDegree(graph, results);
            }
        }

    }

    private void testDegree(final Graph graph, final VertexMemory results) {
        assertEquals(results.getProperty(graph.getVertex(1), DegreeRankVertexProgram.DEGREE), 0l);
        assertEquals(results.getProperty(graph.getVertex(2), DegreeRankVertexProgram.DEGREE), 1l);
        assertEquals(results.getProperty(graph.getVertex(3), DegreeRankVertexProgram.DEGREE), 3l);
        assertEquals(results.getProperty(graph.getVertex(4), DegreeRankVertexProgram.DEGREE), 1l);
        assertEquals(results.getProperty(graph.getVertex(5), DegreeRankVertexProgram.DEGREE), 1l);
        assertEquals(results.getProperty(graph.getVertex(6), DegreeRankVertexProgram.DEGREE), 0l);
    }

}

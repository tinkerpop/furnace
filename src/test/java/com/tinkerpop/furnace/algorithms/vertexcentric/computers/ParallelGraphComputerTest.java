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
public class ParallelGraphComputerTest extends TestCase {

    public void testBasicParallelComputer() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();

        for (int workers = 1; workers < 25; workers++) {
            for (int threads = 1; threads < 25; threads++) {
                DegreeRankProgram program = DegreeRankProgram.create().build();
                ParallelGraphComputer computer = new ParallelGraphComputer(graph, program, GraphComputer.Isolation.BSP);
                computer.execute();
                VertexMemory results = computer.getVertexMemory();
                testDegree(graph, results);
            }
        }

    }

    private void testDegree(final Graph graph, final VertexMemory results) {
        assertEquals(results.getProperty(graph.getVertex(1), DegreeRankProgram.DEGREE), 0l);
        assertEquals(results.getProperty(graph.getVertex(2), DegreeRankProgram.DEGREE), 1l);
        assertEquals(results.getProperty(graph.getVertex(3), DegreeRankProgram.DEGREE), 3l);
        assertEquals(results.getProperty(graph.getVertex(4), DegreeRankProgram.DEGREE), 1l);
        assertEquals(results.getProperty(graph.getVertex(5), DegreeRankProgram.DEGREE), 1l);
        assertEquals(results.getProperty(graph.getVertex(6), DegreeRankProgram.DEGREE), 0l);
    }

}

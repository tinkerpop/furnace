package com.tinkerpop.furnace.algorithms.vertexcentric.programs.swarm;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphComputer;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.computers.SerialGraphComputer;
import junit.framework.TestCase;

import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SwarmProgramTest extends TestCase {

    public void testCoDeveloper() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        SwarmProgram program = SwarmProgram.create().addParticle(new Particle() {
            @Override
            public void execute(Vertex vertex, GraphMemory graphMemory, Long count, Map<Particle, Long> map) {
                if (graphMemory.isInitialIteration()) {
                    SwarmProgram.addParticle(this, 1l, map);
                } else {
                    for (Vertex adjacent : vertex.query().direction(Direction.IN).vertices()) {
                        Map<Particle, Long> others = (Map<Particle, Long>) adjacent.getProperty(SwarmProgram.PARTICLES);
                        for (Map.Entry<Particle, Long> entry : others.entrySet()) {
                            SwarmProgram.addParticle(entry.getKey(), entry.getValue(), map);
                        }
                    }
                }
            }

            @Override
            public int hashCode() {
                return 1;
            }
        }).build();
        SerialGraphComputer computer = new SerialGraphComputer(graph, program, GraphComputer.Isolation.BSP);
        computer.execute();

        VertexMemory results = computer.getVertexMemory();
        for (Vertex vertex : graph.getVertices()) {
            System.out.println(vertex.getProperty("name") + "\t" + results.getProperty(vertex, SwarmProgram.PARTICLES));
        }
    }

}

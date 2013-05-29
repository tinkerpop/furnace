package com.tinkerpop.furnace.computer.programs.swarm;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.computer.GlobalMemory;
import com.tinkerpop.furnace.computer.VertexProgram;

import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SwarmVertexProgram implements VertexProgram {

    private static final String PARTICLES = "particles";


    public void setup(final Vertex vertex, final GlobalMemory globalMemory) {
    }

    public void execute(final Vertex vertex, final GlobalMemory globalMemory) {
        for (final Vertex adjacent : vertex.getVertices(Direction.IN)) {
            final List<Particle> particles = adjacent.getProperty(PARTICLES);
            for (final Particle particle : particles) {
                particle.execute(vertex, globalMemory);
            }
        }
    }
}

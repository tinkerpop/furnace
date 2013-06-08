package com.tinkerpop.furnace.algorithms.vertexcentric.programs.swarm;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SwarmVertexProgram {} /*implements VertexProgram {

    private static final String PARTICLES = "particles";


    public void setup(final Vertex vertex, final GraphMemory globalMemory) {
    }

    public void execute(final Vertex vertex, final GraphMemory globalMemory) {
        for (final Vertex adjacent : vertex.getVertices(Direction.IN)) {
            final List<Particle> particles = adjacent.getProperty(PARTICLES);
            for (final Particle particle : particles) {
                particle.execute(vertex, globalMemory);
            }
        }
    }
} */

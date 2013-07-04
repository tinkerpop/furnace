package com.tinkerpop.furnace.algorithms.vertexcentric.programs.swarm;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.programs.AbstractVertexProgram;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SwarmProgram extends AbstractVertexProgram {

    private Set<Particle> particleTypes = new HashSet<Particle>();

    public static final String PARTICLES = SwarmProgram.class.getName() + ".particles";

    protected SwarmProgram() {
        this.computeKeys.put(PARTICLES, KeyType.CONSTANT);
    }

    public void execute(final Vertex vertex, final GraphMemory graphMemory) {
        if (graphMemory.isInitialIteration()) {
            final Map<Particle, Long> particles = new HashMap<Particle, Long>();
            for (final Particle particle : this.particleTypes) {
                particles.put(particle, 1l);
            }
            vertex.setProperty(PARTICLES, particles);
        } else {
            for (final Map.Entry<Particle, Long> entry : ((Map<Particle, Long>) vertex.getProperty(PARTICLES)).entrySet()) {
                entry.getKey().execute(vertex, graphMemory, entry.getValue());
            }
        }
    }

    public boolean terminate(final GraphMemory graphMemory) {
        return true;
    }

    //////////////////////////////

    public static class Builder {

        private final SwarmProgram vertexProgram = new SwarmProgram();

        public Builder addParticle(final Particle particle) {
            this.vertexProgram.particleTypes.add(particle);
            return this;
        }

        public SwarmProgram build() {
            return this.vertexProgram;
        }
    }

}

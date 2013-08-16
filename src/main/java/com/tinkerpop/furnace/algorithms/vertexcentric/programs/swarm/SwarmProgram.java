package com.tinkerpop.furnace.algorithms.vertexcentric.programs.swarm;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.programs.AbstractVertexProgram;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SwarmProgram extends AbstractVertexProgram {

    private Map<Particle, Long> startParticles = new HashMap<Particle, Long>();

    public static final String PARTICLES = SwarmProgram.class.getName() + ".particles";

    protected SwarmProgram() {
    }

    private void setComputeKeys() {
        this.computeKeys.put(PARTICLES, KeyType.VARIABLE);
        for (final Particle particle : this.startParticles.keySet()) {
            this.computeKeys.putAll(particle.getComputeKeys());
        }
    }

    public void execute(final Vertex vertex, final GraphMemory graphMemory) {
        final Map<Particle, Long> oldParticles = graphMemory.isInitialIteration() ? this.startParticles : (Map<Particle, Long>) vertex.getProperty(PARTICLES);
        final Map<Particle, Long> newParticles = new HashMap<Particle, Long>();

        for (final Map.Entry<Particle, Long> entry : oldParticles.entrySet()) {
            entry.getKey().execute(vertex, graphMemory, entry.getValue(), newParticles);
        }

        vertex.setProperty(PARTICLES, newParticles);
    }

    public static void addParticle(final Particle particle, final Long count, final Map<Particle, Long> newParticles) {
        final Long temp = newParticles.get(particle);
        if (null == temp) {
            newParticles.put(particle, count);
        } else {
            newParticles.put(particle, count + temp);
        }
    }

    public boolean terminate(final GraphMemory graphMemory) {
        return graphMemory.getIteration() >= 3;
    }

    public static Builder create() {
        return new Builder();
    }

    //////////////////////////////

    public static class Builder {

        private final SwarmProgram vertexProgram = new SwarmProgram();

        public Builder addParticle(final Particle particle) {
            SwarmProgram.addParticle(particle, 1l, vertexProgram.startParticles);
            return this;
        }

        public SwarmProgram build() {
            this.vertexProgram.setComputeKeys();
            return this.vertexProgram;
        }
    }

}

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

    private Set<Particle> startParticles = new HashSet<Particle>();

    public static final String PARTICLES = SwarmProgram.class.getName() + ".particles";

    protected SwarmProgram() {
        this.computeKeys.put(PARTICLES, KeyType.VARIABLE);
    }

    public void execute(final Vertex vertex, final GraphMemory graphMemory) {
        if (graphMemory.isInitialIteration()) {
            final Map<Particle, Long> particles = new HashMap<Particle, Long>();
            for (final Particle particle : this.startParticles) {
                particle.execute(vertex, graphMemory, 1l, particles);
            }
            vertex.setProperty(PARTICLES, particles);
        } else {
            final Map<Particle, Long> particles = new HashMap<Particle, Long>();
            for (final Map.Entry<Particle, Long> entry : ((Map<Particle, Long>) vertex.getProperty(PARTICLES)).entrySet()) {
                entry.getKey().execute(vertex, graphMemory, entry.getValue(), particles);
            }
            vertex.setProperty(PARTICLES, particles);
        }
    }

    public static void addParticle(final Particle particle, final Long count, final Map<Particle, Long> map) {
        final Long temp = map.get(particle);
        if (null == temp) {
            map.put(particle, count);
        } else {
            map.put(particle, count + temp);
        }
    }

    public boolean terminate(final GraphMemory graphMemory) {
        return graphMemory.getIteration() > 2;
    }

    public static Builder create() {
        return new Builder();
    }

    //////////////////////////////

    public static class Builder {

        private final SwarmProgram vertexProgram = new SwarmProgram();

        public Builder addParticle(final Particle particle) {
            this.vertexProgram.startParticles.add(particle);
            return this;
        }

        public SwarmProgram build() {
            return this.vertexProgram;
        }
    }

}

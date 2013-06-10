package com.tinkerpop.furnace.algorithms.vertexcentric.programs.swarm;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.programs.AbstractVertexProgram;
import com.tinkerpop.furnace.util.VertexQueryBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SwarmVertexProgram extends AbstractVertexProgram {

    private static final String PARTICLES = "particles";
    private Set<Particle> particleTypes = new HashSet<Particle>();

    protected SwarmVertexProgram() {
        this.computeKeys.put(PARTICLES, KeyType.VARIABLE);
    }

    public void execute(final Vertex vertex, final GraphMemory graphMemory) {
        if (graphMemory.isInitialIteration()) {
            final List<Particle> particles = new ArrayList<Particle>();
            for (final Particle particle : particleTypes) {
                particles.add(particle.createParticle());
            }
            vertex.setProperty(PARTICLES, particles);
        }

        for (final Particle particle : (List<Particle>) vertex.getProperty(PARTICLES)) {
            particle.execute(vertex, graphMemory);
        }
    }

    public boolean terminate(final GraphMemory graphMemory) {
        return true;
    }

    //////////////////////////////

    public static class Builder {

        private final SwarmVertexProgram vertexProgram = new SwarmVertexProgram();

        public Builder outgoing(final VertexQueryBuilder outgoingQuery) {
            this.vertexProgram.outgoingQuery = outgoingQuery;
            return this;
        }

        public Builder incoming(final VertexQueryBuilder incomingQuery) {
            this.vertexProgram.incomingQuery = incomingQuery;
            return this;
        }

        public Builder addParticleType(final Particle particle) {
            this.vertexProgram.particleTypes.add(particle);
            return this;
        }

        public SwarmVertexProgram build() {
            return this.vertexProgram;
        }
    }

}

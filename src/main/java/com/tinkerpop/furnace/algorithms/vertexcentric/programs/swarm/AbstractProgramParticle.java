package com.tinkerpop.furnace.algorithms.vertexcentric.programs.swarm;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexProgram;

import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class AbstractProgramParticle implements Particle {

    protected final VertexProgram program;

    public AbstractProgramParticle(final VertexProgram program) {
        this.program = program;
    }

    public Map<String, VertexProgram.KeyType> getComputeKeys() {
        return this.program.getComputeKeys();
    }

    public void execute(Vertex vertex, GraphMemory graphMemory, Long count, Map<Particle, Long> newParticles) {
        this.program.execute(vertex, graphMemory);
    }
}

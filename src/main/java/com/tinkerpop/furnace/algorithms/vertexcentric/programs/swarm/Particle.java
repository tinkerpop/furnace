package com.tinkerpop.furnace.algorithms.vertexcentric.programs.swarm;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexProgram;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface Particle extends Serializable {

    public Map<String, VertexProgram.KeyType> getComputeKeys();

    public void execute(Vertex vertex, GraphMemory graphMemory, Long count, Map<Particle, Long> newParticles);

}

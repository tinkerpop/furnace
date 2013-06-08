package com.tinkerpop.furnace.algorithms.vertexcentric.programs.swarm;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface Particle {

    public void execute(Vertex vertex, GraphMemory graphMemory);
}

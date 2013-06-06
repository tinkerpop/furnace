package com.tinkerpop.furnace.computer.programs.swarm;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.computer.GraphMemory;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface Particle {

    public void execute(Vertex vertex, GraphMemory graphMemory);
}

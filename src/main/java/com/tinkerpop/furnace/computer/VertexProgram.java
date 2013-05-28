package com.tinkerpop.furnace.computer;

import com.tinkerpop.blueprints.Vertex;

import java.io.Serializable;

/**
 * A VertexProgram represents one component of a distributed graph computation.
 * Each applicable vertex maintains a VertexProgram instance, where the collective behavior of all instances yields the computational result.
 *
 * @author Matthias Broecheler (me@matthiasb.com)
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface VertexProgram extends Serializable {

    /**
     * This method is called prior to the evaluation of the VertexProgram.
     * This method is optional in that it can be skipped by the GraphComputer.
     *
     * @param vertex       the vertex to setup the VertexProgram on
     * @param globalMemory the shared state between all vertices in the computation
     */
    public void setup(Vertex vertex, GlobalMemory globalMemory);

    /**
     * This method denotes the main body of computation.
     *
     * @param vertex       the vertex to execute the VertexProgram on
     * @param globalMemory the shared state between all vertices in the computation
     */
    public void execute(Vertex vertex, GlobalMemory globalMemory);

}

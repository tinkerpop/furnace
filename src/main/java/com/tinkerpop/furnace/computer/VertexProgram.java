package com.tinkerpop.furnace.computer;

import com.tinkerpop.blueprints.Vertex;

import java.io.Serializable;
import java.util.Map;

/**
 * A VertexProgram represents one component of a distributed graph computation.
 * Each applicable vertex maintains a VertexProgram instance, where the collective behavior of all instances yields the computational result.
 *
 * @author Matthias Broecheler (me@matthiasb.com)
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface VertexProgram extends Serializable {

    public enum KeyType {
        VARIABLE,
        CONSTANT
    }

    public void setup(GraphMemory graphMemory);

    /**
     * This method denotes the main body of computation.
     *
     * @param vertex      the vertex to execute the VertexProgram on
     * @param graphMemory the shared state between all vertices in the computation
     */
    public void execute(Vertex vertex, GraphMemory graphMemory);

    public boolean terminate(GraphMemory graphMemory);

    public Map<String, KeyType> getComputeKeys();


}

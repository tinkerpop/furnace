package com.tinkerpop.furnace.algorithms.vertexcentric.programs;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexProgram;
import com.tinkerpop.furnace.util.VertexQueryBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * AbstractVertexProgram provides the requisite fields/methods necessary for a VertexProgram.
 * This is a helper class that reduces the verbosity of a fully-written VertexProgram implementation.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class AbstractVertexProgram implements VertexProgram {

    protected final Map<String, KeyType> computeKeys = new HashMap<String, KeyType>();

    public Map<String, KeyType> getComputeKeys() {
        return computeKeys;
    }

    public void setup(final GraphMemory graphMemory) {

    }

    public String toString() {
        return this.getClass().getSimpleName() + "[" + computeKeys.keySet() + "]";
    }
}

package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.tinkerpop.furnace.algorithms.vertexcentric.GraphComputer;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexProgram;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexSystemMemory;

import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class AbstractVertexMemory implements VertexSystemMemory {

    protected Map<String, VertexProgram.KeyType> computeKeys;
    protected final GraphComputer.Isolation isolation;
    protected boolean phase = true;

    public AbstractVertexMemory(final GraphComputer.Isolation isolation) {
        this.isolation = isolation;
    }

    public void setComputeKeys(final Map<String, VertexProgram.KeyType> computeKeys) {
        this.computeKeys = computeKeys;
    }

    public boolean isComputeKey(final String key) {
        return this.computeKeys.containsKey(key);
    }

    public void completeIteration() {
        this.phase = !this.phase;
    }

    protected String generateGetKey(final String key) {
        final VertexProgram.KeyType keyType = this.computeKeys.get(key);
        if (null == keyType)
            throw new IllegalArgumentException("The provided key is not a compute key: " + key);

        if (keyType.equals(VertexProgram.KeyType.CONSTANT))
            return key;

        if (isolation.equals(GraphComputer.Isolation.BSP))
            return key + !phase;
        else
            return key;

    }

    protected String generateSetKey(final String key) {
        if (this.computeKeys.get(key).equals(VertexProgram.KeyType.CONSTANT))
            return key;

        if (isolation.equals(GraphComputer.Isolation.BSP))
            return key + phase;
        else
            return key;
    }

    protected boolean isConstantKey(final String key) {
        return VertexProgram.KeyType.CONSTANT.equals(this.computeKeys.get(key));
    }
}

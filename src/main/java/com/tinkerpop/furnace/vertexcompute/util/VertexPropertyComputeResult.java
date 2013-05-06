package com.tinkerpop.furnace.vertexcompute.util;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.vertexcompute.ComputeResult;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class VertexPropertyComputeResult implements ComputeResult {

    private final String resultKey;

    public VertexPropertyComputeResult(final String resultKey) {
        this.resultKey = resultKey;
    }

    public <R> R getResult(final Vertex vertex) {
        return vertex.getProperty(this.resultKey);
    }
}

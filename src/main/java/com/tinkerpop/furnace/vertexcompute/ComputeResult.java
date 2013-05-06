package com.tinkerpop.furnace.vertexcompute;

import com.tinkerpop.blueprints.Vertex;

/**
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public interface ComputeResult {

    public <R> R getResult(Vertex vertex);

    // public <R> R getResult(Object vertexId);
}

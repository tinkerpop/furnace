package com.tinkerpop.furnace.vertexcompute;

import com.tinkerpop.blueprints.Vertex;

import java.io.Serializable;

/**
 * (c) Matthias Broecheler (me@matthiasb.com)
 */

public interface VertexComputer extends Serializable {

    /**
     * Only has access to v, the incident edges on v, and the properties of v's neighbors.
     * Can manipulate v's properties. TODO: What about mutating edges?
     * Can access and manipulate the shared state.
     *
     * @param v
     * @param state
     */
    public <R> R compute(Vertex v, SharedState state);

    public boolean applies(Vertex v);

}

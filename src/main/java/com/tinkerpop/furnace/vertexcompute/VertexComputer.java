package com.tinkerpop.furnace.vertexcompute;

import com.tinkerpop.blueprints.Vertex;

import java.io.Serializable;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public interface VertexComputer extends Serializable {

    public void setup(Vertex vertex, SharedState state);

    public <R> R compute(Vertex vertex, SharedState state);

    public void cleanup(Vertex vertex, SharedState state);

}

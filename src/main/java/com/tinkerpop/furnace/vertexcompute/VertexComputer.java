package com.tinkerpop.furnace.vertexcompute;

import com.tinkerpop.blueprints.Vertex;

import java.io.Serializable;

/**
 * A VertexComputer represents one component of a distributed graph computation.
 * Each applicable vertex maintains a VertexComputer instance, where the collective behavior of all instances yields the computational result.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public interface VertexComputer extends Serializable {

    /**
     * This method is called prior to the evaluation of the VertexComputer.
     * This method is optional in that it can be skipped by the GraphComputer.
     *
     * @param vertex      the vertex to setup the VertexComputer on
     * @param sharedState the shared state between all vertices in the computation
     */
    public void setup(Vertex vertex, SharedState sharedState);

    /**
     * This method denotes the main body of computation.
     *
     * @param vertex      the vertex to execute the VertexComputer on
     * @param sharedState the shared state between all vertices in the computation
     */
    public <R> R compute(Vertex vertex, SharedState sharedState);

    /**
     * This method is called by GraphComputer.cleanup() and is intended to remove all computational side-effects from the graph.
     *
     * @param vertex      the vertex to cleanup the VertexComputer on
     * @param sharedState the shared state between all vertices in the computation
     */
    public void cleanup(Vertex vertex, SharedState sharedState);

}

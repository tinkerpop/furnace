package com.tinkerpop.furnace.vertexcompute;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public interface VertexComputerGraph {

    public ComputeResult execute(GraphComputer graphComputer);

    public void cleanup(GraphComputer graphComputer);

    // public SharedState createDefaultSharedState()
    // SharedState is ultimately tied to the implemented VertexComputerGraph and thus, can be provided by the graph.

}

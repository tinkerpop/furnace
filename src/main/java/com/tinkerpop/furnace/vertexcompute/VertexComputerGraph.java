package com.tinkerpop.furnace.vertexcompute;

/**
 * A VertexComputerGraph is a Graph that supports the execution of a vertex-centric, graph computer.
 * This model of graph computing is theoretically realized as each vertex maintaining a thread of execution running a VertexComputer.
 * The VertexComputers communicate with one another over the edges of the graph in that the communication topology is the graph topology.
 * A GraphComputer coordinates the creation, execution, shutdown, and result aggregation of the VertexComputers.
 * The behavior of the VertexComputer implementation ultimately determines the semantics of the results returned by the computation.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public interface VertexComputerGraph {

    public ComputerProperties execute(GraphComputer graphComputer);

}

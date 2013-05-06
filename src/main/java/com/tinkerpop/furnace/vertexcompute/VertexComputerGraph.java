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

    /**
     * Execute the provided GraphComputer on the graph.
     *
     * @param graphComputer the GraphComputer to evaluate on the graph
     * @return the results of the computation
     */
    public ComputeResult execute(GraphComputer graphComputer);

    /**
     * Cleanup any metadata on the graph that was used during the GraphComputer's execution.
     * In many situations, the results of the computation are stored in the graph as the graph serves as a distributed hashmap.
     * The purpose of this method is to remove the computational side-effects left during execution.
     *
     * @param graphComputer the GraphComputer that was evaluated on the graph
     */
    public void cleanup(GraphComputer graphComputer);

    // public SharedState createDefaultSharedState()
    // SharedState is ultimately tied to the implemented VertexComputerGraph and thus, can be provided by the graph.
    // e.g. single thread can use a non-thread safe map.
    // e.g. a concurrent one must use a synchronized implementation
    // e.g. Hadoop must used the DistributedCache
    // However, the user should be able to specify a different SharedState if desired (e.g. better implementations).

}

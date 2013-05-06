package com.tinkerpop.furnace.vertexcompute;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public interface VertexComputerGraph {

    public ComputeResult execute(GraphComputer graphComputer);

    public void cleanup(GraphComputer graphComputer);

}

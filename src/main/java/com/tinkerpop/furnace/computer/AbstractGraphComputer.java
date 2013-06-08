package com.tinkerpop.furnace.computer;

import com.tinkerpop.blueprints.Graph;

/**
 * AbstractGraphComputer provides the requisite fields necessary for a GraphComputer.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class AbstractGraphComputer implements GraphComputer {

    protected final Graph graph;
    protected VertexProgram vertexProgram;
    protected GraphSystemMemory graphMemory;
    protected VertexSystemMemory vertexMemory;
    protected Isolation isolation;

    public AbstractGraphComputer(final Graph graph, final VertexProgram vertexProgram,
                                 final GraphSystemMemory graphMemory, final VertexSystemMemory vertexMemory,
                                 final Isolation isolation) {
        this.graph = graph;
        this.vertexProgram = vertexProgram;
        this.graphMemory = graphMemory;
        this.vertexMemory = vertexMemory;
        this.isolation = isolation;
    }

    public VertexMemory getVertexMemory() {
        return this.vertexMemory;
    }

    public GraphMemory getGraphMemory() {
        return this.graphMemory;
    }
}

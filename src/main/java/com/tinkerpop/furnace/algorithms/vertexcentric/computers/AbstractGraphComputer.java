package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphComputer;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphSystemMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexProgram;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexSystemMemory;

/**
 * AbstractGraphComputer provides the requisite fields/methods necessary for a GraphComputer.
 * This is a helper class that reduces the verbosity of a fully-written GraphComputer implementation.
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

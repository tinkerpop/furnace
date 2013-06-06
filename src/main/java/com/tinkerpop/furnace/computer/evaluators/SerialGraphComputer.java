package com.tinkerpop.furnace.computer.evaluators;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.computer.GraphComputer;
import com.tinkerpop.furnace.computer.GraphMemory;
import com.tinkerpop.furnace.computer.GraphSystemMemory;
import com.tinkerpop.furnace.computer.VertexMemory;
import com.tinkerpop.furnace.computer.VertexProgram;
import com.tinkerpop.furnace.computer.VertexSystemMemory;
import com.tinkerpop.furnace.computer.memory.SimpleGraphMemory;
import com.tinkerpop.furnace.computer.memory.SimpleVertexMemory;

/**
 * A SerialGraphComputer implements the graph computing model in a serial, single-threaded manner.
 * This implementation is simple and serves as a reference implementation for the semantics of the interfaces.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SerialGraphComputer implements GraphComputer {

    protected final Graph graph;
    protected final VertexProgram vertexProgram;
    protected final VertexSystemMemory vertexMemory;
    protected final GraphSystemMemory graphMemory = new SimpleGraphMemory();

    public SerialGraphComputer(final VertexProgram vertexProgram, final Graph graph, final Isolation isolation) {
        this.vertexProgram = vertexProgram;
        this.graph = graph;
        this.vertexMemory = new SimpleVertexMemory(isolation);
    }

    public VertexMemory getVertexMemory() {
        return this.vertexMemory;
    }

    public GraphMemory getGraphMemory() {
        return this.graphMemory;
    }

    public void execute() {
        final long time = System.currentTimeMillis();
        this.vertexMemory.setComputeKeys(this.vertexProgram.getComputeKeys());
        this.vertexProgram.setup(this.graphMemory);

        final CoreShellVertex coreShellVertex = new CoreShellVertex(this.vertexMemory);

        Preconditions.checkArgument(this.graph.getFeatures().supportsVertexIteration);

        while (!this.vertexProgram.terminate(this.graphMemory)) {
            for (final Vertex vertex : this.graph.getVertices()) {
                coreShellVertex.setBaseVertex(vertex);
                this.vertexProgram.execute(coreShellVertex, this.graphMemory);
            }
            this.vertexMemory.completeIteration();
            this.graphMemory.incrIteration();
        }

        this.graphMemory.setRuntime(System.currentTimeMillis() - time);
    }
}

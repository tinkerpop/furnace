package com.tinkerpop.furnace.computer.computers;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.computer.AbstractGraphComputer;
import com.tinkerpop.furnace.computer.VertexProgram;

/**
 * A SerialGraphComputer implements the graph computing model in a serial, single-threaded manner.
 * This implementation is simple and serves as a reference implementation for the semantics of the interfaces.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SerialGraphComputer extends AbstractGraphComputer {

    public SerialGraphComputer(final Graph graph, final VertexProgram vertexProgram, final Isolation isolation) {
        super(graph, vertexProgram, new SimpleGraphMemory(), new SimpleVertexMemory(isolation), isolation);
    }

    public void execute() {
        final long time = System.currentTimeMillis();
        this.vertexMemory.setComputeKeys(this.vertexProgram.getComputeKeys());
        this.vertexProgram.setup(this.graphMemory);

        final CoreShellVertex coreShellVertex = new CoreShellVertex(this.vertexMemory);

        Preconditions.checkArgument(this.graph.getFeatures().supportsVertexIteration);

        boolean done = false;
        while (!done) {
            for (final Vertex vertex : this.graph.getVertices()) {
                coreShellVertex.setBaseVertex(vertex);
                this.vertexProgram.execute(coreShellVertex, this.graphMemory);
            }
            this.vertexMemory.completeIteration();
            this.graphMemory.incrIteration();
            done = this.vertexProgram.terminate(this.graphMemory);
        }

        this.graphMemory.setRuntime(System.currentTimeMillis() - time);
    }
}

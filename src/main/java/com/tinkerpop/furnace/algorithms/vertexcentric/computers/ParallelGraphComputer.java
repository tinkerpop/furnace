package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexProgram;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ParallelGraphComputer extends AbstractGraphComputer {

    int threads = Runtime.getRuntime().availableProcessors() - 1;
    int chunkSize = 1000;

    public ParallelGraphComputer(final Graph graph, final VertexProgram vertexProgram, final Isolation isolation) {
        super(graph, vertexProgram, new SimpleGraphMemory(), new SimpleVertexMemory(isolation), isolation);
    }

    public ParallelGraphComputer(final Graph graph, final VertexProgram vertexProgram, final Isolation isolation, final int threads) {
        this(graph, vertexProgram, isolation);
        this.threads = threads;
    }

    public void execute() {
        Preconditions.checkArgument(this.graph.getFeatures().supportsVertexIteration);
        final long time = System.currentTimeMillis();
        this.vertexMemory.setComputeKeys(this.vertexProgram.getComputeKeys());
        this.vertexProgram.setup(this.graphMemory);

        boolean done = false;
        while (!done) {
            final ExecutorService executor = Executors.newFixedThreadPool(this.threads);
            final Iterator<Vertex> vertices = this.graph.getVertices().iterator();
            while (vertices.hasNext()) {
                final Runnable worker = new VertexThread(vertices);
                executor.execute(worker);
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            this.vertexMemory.completeIteration();
            this.graphMemory.incrIteration();
            done = this.vertexProgram.terminate(this.graphMemory);
        }

        this.graphMemory.setRuntime(System.currentTimeMillis() - time);
    }

    public class VertexThread implements Runnable {

        private final List<Vertex> vertices = new ArrayList<Vertex>();

        public VertexThread(final Iterator<Vertex> vertices) {
            for (int i = 0; i < chunkSize; i++) {
                if (!vertices.hasNext())
                    break;
                this.vertices.add(vertices.next());
            }
        }

        public void run() {
            final CoreShellVertex coreShellVertex = new CoreShellVertex(vertexMemory);
            for (final Vertex vertex : this.vertices) {
                //System.out.println(this + " " + vertex);
                coreShellVertex.setBaseVertex(vertex);
                vertexProgram.execute(coreShellVertex, graphMemory);
            }
        }

    }
}

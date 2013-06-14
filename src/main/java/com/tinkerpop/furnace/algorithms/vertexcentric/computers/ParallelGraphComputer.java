package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexProgram;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ParallelGraphComputer extends AbstractGraphComputer {

    long totalVertices = 1000;
    int threads = 10;
    long workers = 100;

    public ParallelGraphComputer(final Graph graph, final VertexProgram vertexProgram, final Isolation isolation) {
        super(graph, vertexProgram, new SimpleGraphMemory(), new SimpleVertexMemory(isolation), isolation);
    }

    public ParallelGraphComputer(final Graph graph, final VertexProgram vertexProgram, final Isolation isolation, final int threads, final int workers, final long totalVertices) {
        this(graph, vertexProgram, isolation);
        this.threads = threads;
        this.workers = workers > totalVertices ? totalVertices : workers;
        this.totalVertices = totalVertices;
    }

    public void execute() {
        Preconditions.checkArgument(this.graph.getFeatures().supportsVertexIteration);
        final long time = System.currentTimeMillis();
        this.vertexMemory.setComputeKeys(this.vertexProgram.getComputeKeys());
        this.vertexProgram.setup(this.graphMemory);

        long limit = this.totalVertices / this.workers;
        if (totalVertices % workers != 0)
            limit = (totalVertices / workers) + 1;

        boolean done = false;
        while (!done) {
            final ExecutorService executor = Executors.newFixedThreadPool(this.threads);
            for (int i = 0; i < this.workers; i++) {
                long skip = i * limit;
                final Runnable worker = new VertexThread(skip, i - 1 == this.workers ? Long.MAX_VALUE : limit);
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

        private final Iterable<Vertex> vertices;

        public VertexThread(final long skip, final long limit) {
            this.vertices = graph.query().limit(skip, limit).vertices();
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
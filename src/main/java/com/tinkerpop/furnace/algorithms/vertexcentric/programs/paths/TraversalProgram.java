package com.tinkerpop.furnace.algorithms.vertexcentric.programs.paths;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.programs.AbstractVertexProgram;
import com.tinkerpop.furnace.util.VertexQueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class TraversalProgram extends AbstractVertexProgram {

    public static final String COUNTS = "counts";
    private int totalIterations = 30;
    private final List<VertexQueryBuilder> queries = new ArrayList<VertexQueryBuilder>();

    protected TraversalProgram() {
        computeKeys.put(COUNTS, KeyType.VARIABLE);
    }

    public void setup(final GraphMemory graphMemory) {

    }

    public void execute(final Vertex vertex, final GraphMemory graphMemory) {
        if (graphMemory.isInitialIteration()) {
            vertex.setProperty(COUNTS, 1l);
        } else {
            final VertexQueryBuilder query = this.queries.get((graphMemory.getIteration() - 1) % this.queries.size());
            long newCount = 0l;
            for (final Vertex adjacent : query.build(vertex).vertices()) {
                newCount = newCount + (Long) adjacent.getProperty(COUNTS);
            }
            vertex.setProperty(COUNTS, newCount);
        }
    }

    public boolean terminate(final GraphMemory graphMemory) {
        return graphMemory.getIteration() >= this.totalIterations;
    }

    public static Builder create() {
        return new Builder();
    }

    //////////////////////////////

    public static class Builder {

        private final TraversalProgram vertexProgram = new TraversalProgram();

        private int totalIterations = 0;

        public Builder iterations(final int iterations) {
            this.totalIterations = iterations;
            return this;
        }

        public Builder addQuery(final VertexQueryBuilder query) {
            this.vertexProgram.queries.add(query);
            return this;
        }

        public TraversalProgram build() {
            this.vertexProgram.totalIterations = (this.totalIterations * this.vertexProgram.queries.size()) + 1;
            return this.vertexProgram;
        }
    }
}

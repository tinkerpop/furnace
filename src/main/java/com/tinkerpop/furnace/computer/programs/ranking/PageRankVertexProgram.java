package com.tinkerpop.furnace.computer.programs.ranking;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.computer.GraphMemory;
import com.tinkerpop.furnace.computer.VertexProgram;
import com.tinkerpop.furnace.util.VertexQueryBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * A VertexProgram for the popular PageRank algorithm.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankVertexProgram implements VertexProgram {

    public static final String PAGE_RANK = "pageRank";
    public static final String EDGE_COUNT = "edgeCount";

    private VertexQueryBuilder outgoingQuery = new VertexQueryBuilder().direction(Direction.OUT);
    private VertexQueryBuilder incomingQuery = new VertexQueryBuilder().direction(Direction.IN);

    private double vertexCountAsDouble = 100;
    private double alpha = 0.85d;
    private int totalIterations = 30;

    private static final Map<String, KeyType> computeKeys = new HashMap<String, KeyType>();

    static {
        computeKeys.put(PAGE_RANK, KeyType.VARIABLE);
        computeKeys.put(EDGE_COUNT, KeyType.CONSTANT);
    }

    protected PageRankVertexProgram() {
    }

    public void setup(final GraphMemory graphMemory) {

    }

    public void execute(final Vertex vertex, final GraphMemory graphMemory) {
        if (graphMemory.isInitialIteration()) {
            vertex.setProperty(PAGE_RANK, 1.0d / this.vertexCountAsDouble);
            vertex.setProperty(EDGE_COUNT, (double) this.outgoingQuery.build(vertex).count());
        } else {
            double newPageRank = 0.0d;
            for (final Vertex adjacent : this.incomingQuery.build(vertex).vertices()) {
                newPageRank += (Double) adjacent.getProperty(PAGE_RANK) / (Double) adjacent.getProperty(EDGE_COUNT);
            }
            vertex.setProperty(PAGE_RANK, (this.alpha * newPageRank) + ((1.0d - this.alpha) / this.vertexCountAsDouble));
            vertex.setProperty(EDGE_COUNT, vertex.getProperty(EDGE_COUNT));
        }
    }

    public Map<String, KeyType> getComputeKeys() {
        return computeKeys;
    }

    public boolean terminate(final GraphMemory graphMemory) {
        return graphMemory.getIteration() >= this.totalIterations;
    }

    public static Builder create() {
        return new Builder();
    }

    //////////////////////////////

    public static class Builder {

        private final PageRankVertexProgram vertexProgram = new PageRankVertexProgram();

        public Builder iterations(final int iterations) {
            this.vertexProgram.totalIterations = iterations;
            return this;
        }

        public Builder alpha(final double alpha) {
            this.vertexProgram.alpha = alpha;
            return this;
        }

        public Builder outgoing(final VertexQueryBuilder outgoingQuery) {
            this.vertexProgram.outgoingQuery = outgoingQuery;
            return this;
        }

        public Builder incoming(final VertexQueryBuilder incomingQuery) {
            this.vertexProgram.incomingQuery = incomingQuery;
            return this;
        }

        public Builder vertexCount(final int count) {
            this.vertexProgram.vertexCountAsDouble = (double) count;
            return this;
        }

        public PageRankVertexProgram build() {
            return this.vertexProgram;
        }
    }

}

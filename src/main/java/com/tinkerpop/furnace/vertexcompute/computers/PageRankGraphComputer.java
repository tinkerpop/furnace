package com.tinkerpop.furnace.vertexcompute.computers;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import com.tinkerpop.furnace.vertexcompute.GraphComputer;
import com.tinkerpop.furnace.vertexcompute.GraphComputerBuilder;

import java.util.Arrays;

/**
 * The PageRankGraphComputer provides a vertex-centric implementation of the popular PageRank algorithm.
 * This implementation is sink-unaware in that a sink vertex does not equally distribute its rank score across the entire graph.
 * A drawback of such an implementation is that the final probability distribution does not sum 1.0.
 * Moreover, if numerous sinks exists, then floating point issues can arise as the primary contributor of energy is the (1-alpha)/|V| score.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankGraphComputer extends GraphComputer {

    private int iterations = 0;
    protected int totalIterations;

    public static final String PAGE_RANK = "pageRank";
    public static final String EDGE_COUNT = "edgeCount";

    public static final String VERTEX_COUNT = "vertexCount";
    public static final String ALPHA = "alpha";

    protected PageRankGraphComputer() {
        super();
    }

    public boolean terminate() {
        return iterations++ == totalIterations;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder extends GraphComputerBuilder {

        protected double alpha = 0.85d;
        protected long vertexCount;
        protected int iterations = 30;
        protected VertexQueryBuilder incomingQuery = new VertexQueryBuilder().direction(Direction.IN);
        protected VertexQueryBuilder outgoingQuery = new VertexQueryBuilder().direction(Direction.OUT);

        protected Builder() {
        }

        public Builder alpha(final double alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder vertexCount(final long vertexCount) {
            this.vertexCount = vertexCount;
            return this;
        }

        public Builder iterations(final int iterations) {
            this.iterations = iterations;
            return this;
        }

        public Builder incoming(final VertexQueryBuilder builder) {
            this.incomingQuery = builder;
            return this;
        }

        public Builder outgoing(final VertexQueryBuilder builder) {
            this.outgoingQuery = builder;
            return this;
        }

        public PageRankGraphComputer build() {
            super.build();
            Preconditions.checkNotNull(this.alpha);
            Preconditions.checkNotNull(this.vertexCount);
            Preconditions.checkNotNull(this.iterations);
            Preconditions.checkNotNull(this.incomingQuery);
            Preconditions.checkNotNull(this.outgoingQuery);

            final PageRankGraphComputer computer = new PageRankGraphComputer();
            this.sharedState.setIfAbsent(ALPHA, this.alpha);
            this.sharedState.setIfAbsent(VERTEX_COUNT, this.vertexCount);
            computer.sharedState = this.sharedState;
            computer.isolation = this.isolation;
            computer.computerProperties = this.computerProperties;
            computer.totalIterations = this.iterations;
            computer.vertexComputer = new PageRankVertexComputer(this.outgoingQuery, this.incomingQuery);
            computer.computeKeys = Arrays.asList(PAGE_RANK, EDGE_COUNT);
            return computer;
        }
    }
}
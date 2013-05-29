package com.tinkerpop.furnace.computer.programs.ranking;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.furnace.computer.Evaluator;
import com.tinkerpop.furnace.computer.GlobalMemory;
import com.tinkerpop.furnace.computer.GraphComputer;
import com.tinkerpop.furnace.computer.GraphComputerBuilder;
import com.tinkerpop.furnace.computer.Isolation;
import com.tinkerpop.furnace.computer.LocalMemory;
import com.tinkerpop.furnace.util.VertexQueryBuilder;

/**
 * The PageRankGraphComputer provides a vertex-centric implementation of the popular PageRank algorithm.
 * This implementation is sink-unaware in that a sink vertex does not equally distribute its rank score across the entire graph.
 * A drawback of such an implementation is that the final probability distribution does not sum 1.0.
 * Moreover, if numerous sinks exists, then floating point issues can arise as the primary contributor of energy is the (1-alpha)/|V| score.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankGraphComputer extends GraphComputer {

    protected int totalIterations;

    public static final String PAGE_RANK = "pageRank";
    public static final String EDGE_COUNT = "edgeCount";

    public static final String VERTEX_COUNT = "vertexCount";
    public static final String ALPHA = "alpha";

    protected PageRankGraphComputer() {
        super();
    }

    public boolean terminate() {
        return this.getGlobalMemory().getIteration() >= this.totalIterations;
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

        public Builder isolation(final Isolation isolation) {
            super.isolation(isolation);
            return this;
        }

        public Builder globalMemory(final GlobalMemory globalMemory) {
            super.globalMemory(globalMemory);
            return this;
        }

        public Builder localMemory(final LocalMemory localMemory) {
            super.localMemory(localMemory);
            return this;
        }

        public Builder evaluator(final Evaluator evaluator) {
            super.evaluator(evaluator);
            return this;
        }

        public Builder graph(final Graph graph) {
            super.graph(graph);
            return this;
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
            this.globalMemory.setIfAbsent(ALPHA, this.alpha);
            this.globalMemory.setIfAbsent(VERTEX_COUNT, this.vertexCount);
            computer.globalMemory = this.globalMemory;
            computer.isolation = this.isolation;

            computer.localMemory = this.localMemory;
            computer.localMemory.setComputeKeys(PAGE_RANK);
            computer.localMemory.setFinalComputeKeys(EDGE_COUNT);
            computer.localMemory.setIsolation(this.isolation);

            computer.totalIterations = this.iterations;
            computer.vertexProgram = new PageRankVertexProgram(this.outgoingQuery, this.incomingQuery);
            computer.evaluator = this.evaluator;
            computer.graph = graph;
            return computer;
        }
    }
}
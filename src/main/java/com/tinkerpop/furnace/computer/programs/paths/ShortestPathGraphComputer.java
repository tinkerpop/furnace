package com.tinkerpop.furnace.computer.programs.paths;

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
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ShortestPathGraphComputer extends GraphComputer {

    protected int totalIterations;

    public static final String LIVE_PATHS = "livePaths";
    public static final String DEAD_PATHS = "deadPaths";

    protected ShortestPathGraphComputer() {
        super();
    }

    public boolean terminate() {
        return this.getGlobalMemory().getIteration() >= this.totalIterations;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder extends GraphComputerBuilder {

        protected int iterations = 30;
        protected VertexQueryBuilder incomingQuery = new VertexQueryBuilder().direction(Direction.IN);

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

        public Builder iterations(final int iterations) {
            this.iterations = iterations;
            return this;
        }

        public Builder incoming(final VertexQueryBuilder builder) {
            this.incomingQuery = builder;
            return this;
        }

        public ShortestPathGraphComputer build() {
            super.build();
            Preconditions.checkNotNull(this.iterations);
            Preconditions.checkNotNull(this.incomingQuery);

            final ShortestPathGraphComputer computer = new ShortestPathGraphComputer();
            computer.globalMemory = this.globalMemory;
            computer.isolation = this.isolation;

            computer.localMemory = this.localMemory;
            computer.localMemory.setComputeKeys(LIVE_PATHS, DEAD_PATHS);
            computer.localMemory.setIsolation(this.isolation);

            computer.totalIterations = this.iterations;
            computer.vertexProgram = new ShortestPathVertexProgram(this.incomingQuery);
            computer.evaluator = this.evaluator;
            computer.graph = graph;
            return computer;
        }
    }
}
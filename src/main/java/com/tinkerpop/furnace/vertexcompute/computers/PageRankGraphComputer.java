package com.tinkerpop.furnace.vertexcompute.computers;

import com.google.common.base.Preconditions;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import com.tinkerpop.furnace.vertexcompute.ComputeResult;
import com.tinkerpop.furnace.vertexcompute.GraphComputer;
import com.tinkerpop.furnace.vertexcompute.GraphComputerBuilder;
import com.tinkerpop.furnace.vertexcompute.SharedState;
import com.tinkerpop.furnace.vertexcompute.util.VertexPropertyComputeResult;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankGraphComputer extends GraphComputer {

    private int iterations = 0;
    private final int totalIterations;
    private final String pageRankKey;

    public static final String VERTEX_COUNT = "vertexCount";
    public static final String ALPHA = "alpha";
    public static final String PAGE_RANK = "pageRank";

    protected PageRankGraphComputer(final String keyPrefix, final double alpha, final long vertexCount,
                                    final int totalIterations, final VertexQueryBuilder builder,
                                    final SharedState sharedState, final Isolation isolation) {
        super(new PageRankVertexComputer(keyPrefix + "." + PAGE_RANK, builder), sharedState, isolation);

        this.totalIterations = totalIterations;
        this.pageRankKey = keyPrefix + "." + PAGE_RANK;
        this.getSharedState().set(VERTEX_COUNT, vertexCount);
        this.getSharedState().set(ALPHA, alpha);
    }

    public boolean terminate() {
        return iterations++ == totalIterations;
    }

    public ComputeResult generateResult() {
        return new VertexPropertyComputeResult(this.pageRankKey);
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder extends GraphComputerBuilder {

        protected double alpha = 0.85d;
        protected long vertexCount;
        protected int iterations = 30;
        protected String prefix;
        protected VertexQueryBuilder queryBuilder = new VertexQueryBuilder();

        protected Builder() {
        }

        public Builder prefix(final String prefix) {
            this.prefix = prefix;
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

        public Builder query(final VertexQueryBuilder builder) {
            this.queryBuilder = builder;
            return this;
        }

        public PageRankGraphComputer build() {
            super.build();
            Preconditions.checkNotNull(this.prefix);
            Preconditions.checkNotNull(this.alpha);
            Preconditions.checkNotNull(this.vertexCount);
            Preconditions.checkNotNull(this.iterations);
            Preconditions.checkNotNull(this.queryBuilder);

            return new PageRankGraphComputer(this.prefix, this.alpha, this.vertexCount, this.iterations, this.queryBuilder, this.sharedState, this.isolation);
        }
    }
}
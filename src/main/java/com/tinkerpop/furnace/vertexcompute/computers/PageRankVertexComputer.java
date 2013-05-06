package com.tinkerpop.furnace.vertexcompute.computers;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import com.tinkerpop.furnace.vertexcompute.SharedState;
import com.tinkerpop.furnace.vertexcompute.VertexComputer;

/**
 * A VertexComputer for the popular PageRank algorithm.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankVertexComputer implements VertexComputer {

    private final String pageRankKey;
    private final String pageRankTempKey;
    private final VertexQueryBuilder builder;

    private double vertexCountAsDouble;
    private double alpha;
    private double oneMinusAlpha;

    public PageRankVertexComputer(final String pageRankKey, final VertexQueryBuilder builder) {
        this.pageRankKey = pageRankKey;
        this.pageRankTempKey = pageRankKey + "Temp"; // isolation levels dictate computation (future)
        this.builder = builder;
    }

    public void setup(final Vertex vertex, final SharedState sharedState) {
        this.vertexCountAsDouble = ((Number) sharedState.get(PageRankGraphComputer.VERTEX_COUNT)).doubleValue();
        this.alpha = (Double) sharedState.get(PageRankGraphComputer.ALPHA);
        this.oneMinusAlpha = 1.0d - this.alpha;
        vertex.setProperty(this.pageRankKey, 1.0d / this.vertexCountAsDouble);
        vertex.setProperty(this.pageRankTempKey, 0.0d);
    }

    public <R> R compute(final Vertex vertex, final SharedState sharedState) {
        vertex.setProperty(this.pageRankKey, (this.alpha * (Double) vertex.getProperty(this.pageRankTempKey)) + (this.oneMinusAlpha / this.vertexCountAsDouble));
        vertex.setProperty(this.pageRankTempKey, 0.0d);
        final VertexQuery query = this.builder.build(vertex);
        final double pageRank = vertex.getProperty(this.pageRankKey);
        final double distribution = pageRank / query.count();
        for (final Vertex other : query.vertices()) {
            double otherEnergy = other.getProperty(this.pageRankTempKey);
            other.setProperty(this.pageRankTempKey, distribution + otherEnergy);
        }
        return null;
    }

    public void cleanup(final Vertex vertex, final SharedState sharedState) {
        vertex.removeProperty(this.pageRankKey);
        vertex.removeProperty(this.pageRankTempKey);
    }
}

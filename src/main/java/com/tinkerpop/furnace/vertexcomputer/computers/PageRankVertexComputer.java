package com.tinkerpop.furnace.vertexcomputer.computers;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import com.tinkerpop.furnace.vertexcomputer.SharedState;
import com.tinkerpop.furnace.vertexcomputer.VertexComputer;

/**
 * A VertexComputer for the popular PageRank algorithm.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankVertexComputer implements VertexComputer {

    private final VertexQueryBuilder outgoingQuery;
    private final VertexQueryBuilder incomingQuery;

    private double vertexCountAsDouble;
    private double alpha;

    public PageRankVertexComputer(final VertexQueryBuilder outgoingQuery, final VertexQueryBuilder incomingQuery) {
        this.outgoingQuery = outgoingQuery;
        this.incomingQuery = incomingQuery;
    }

    public void setup(final Vertex vertex, final SharedState sharedState) {
        this.vertexCountAsDouble = ((Number) sharedState.get(PageRankGraphComputer.VERTEX_COUNT)).doubleValue();
        this.alpha = (Double) sharedState.get(PageRankGraphComputer.ALPHA);
        vertex.setProperty(PageRankGraphComputer.PAGE_RANK, 1.0d / this.vertexCountAsDouble);
        vertex.setProperty(PageRankGraphComputer.EDGE_COUNT, new Double(this.outgoingQuery.build(vertex).count()));
    }

    public <R> R compute(final Vertex vertex, final SharedState sharedState) {
        final Iterable<Vertex> adjacents = this.incomingQuery.build(vertex).vertices();
        Double pageRankValue = vertex.getProperty(PageRankGraphComputer.PAGE_RANK);
        for (final Vertex adjacent : adjacents) {
            pageRankValue += (Double) adjacent.getProperty(PageRankGraphComputer.PAGE_RANK) / (Double) adjacent.getProperty(PageRankGraphComputer.EDGE_COUNT);
        }
        vertex.setProperty(PageRankGraphComputer.PAGE_RANK, (this.alpha * pageRankValue) + ((1.0d - this.alpha) / this.vertexCountAsDouble));
        return (R) pageRankValue;
    }
}

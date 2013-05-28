package com.tinkerpop.furnace.computer.programs;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.computer.GlobalMemory;
import com.tinkerpop.furnace.computer.VertexProgram;
import com.tinkerpop.furnace.util.VertexQueryBuilder;

/**
 * A VertexComputer for the popular PageRank algorithm.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PageRankVertexProgram implements VertexProgram {

    private final VertexQueryBuilder outgoingQuery;
    private final VertexQueryBuilder incomingQuery;

    private double vertexCountAsDouble;
    private double alpha;

    public PageRankVertexProgram(final VertexQueryBuilder outgoingQuery, final VertexQueryBuilder incomingQuery) {
        this.outgoingQuery = outgoingQuery;
        this.incomingQuery = incomingQuery;
    }

    public void setup(final Vertex vertex, final GlobalMemory globalMemory) {
        this.vertexCountAsDouble = ((Number) globalMemory.get(PageRankGraphComputer.VERTEX_COUNT)).doubleValue();
        this.alpha = (Double) globalMemory.get(PageRankGraphComputer.ALPHA);
        vertex.setProperty(PageRankGraphComputer.PAGE_RANK, 1.0d / this.vertexCountAsDouble);
        vertex.setProperty(PageRankGraphComputer.EDGE_COUNT, (double) this.outgoingQuery.build(vertex).count());
    }

    public void execute(final Vertex vertex, final GlobalMemory globalMemory) {
        double newPageRank = 0.0d;
        for (final Vertex adjacent : this.incomingQuery.build(vertex).vertices()) {
            newPageRank += (Double) adjacent.getProperty(PageRankGraphComputer.PAGE_RANK) / (Double) adjacent.getProperty(PageRankGraphComputer.EDGE_COUNT);
        }
        vertex.setProperty(PageRankGraphComputer.PAGE_RANK, (this.alpha * newPageRank) + ((1.0d - this.alpha) / this.vertexCountAsDouble));
        vertex.setProperty(PageRankGraphComputer.EDGE_COUNT, vertex.getProperty(PageRankGraphComputer.EDGE_COUNT));
    }
}

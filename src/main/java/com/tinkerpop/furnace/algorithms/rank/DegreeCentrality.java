package com.tinkerpop.furnace.algorithms.rank;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.util.VertexQueryBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DegreeCentrality implements GlobalGraphRank<Map<Vertex, Long>> {
    @Override
    public Map<Vertex, Long> compute(final Graph graph, final VertexQueryBuilder query) {
        final Map<Vertex, Long> rank = new HashMap<Vertex, Long>();
        for (final Vertex vertex : graph.getVertices()) {
            rank.put(vertex, query.build(vertex).count());
        }
        return rank;
    }
}

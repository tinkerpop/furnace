package com.tinkerpop.furnace.algorithms;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.DefaultQuery;
import com.tinkerpop.furnace.util.QueryTemplate;

import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface GlobalGraphRank<A extends Map<Vertex, ? extends Number>> {

    public A compute(final Graph graph, final QueryTemplate query);
}

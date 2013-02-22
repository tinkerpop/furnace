package com.tinkerpop.furnace.algorithms.rank;

import java.util.Map;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.util.QueryTemplate;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface GlobalGraphRank<A extends Map<Vertex, ? extends Number>> {

	public A compute(final Graph graph, final QueryTemplate query);
}

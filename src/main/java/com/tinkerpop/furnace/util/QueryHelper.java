package com.tinkerpop.furnace.util;

import com.tinkerpop.blueprints.Query;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.util.QueryTemplate.HasContainer;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class QueryHelper {

	public static Query createQuery(final Vertex vertex, final QueryTemplate queryTemplate) {
		final Query query = vertex.query();
		for (final HasContainer hasContainer : queryTemplate.hasContainers) {
			if (hasContainer.compare.equals(Query.Compare.EQUAL)) {
				query.has(hasContainer.key, hasContainer.value);
			} else {
				query.has(hasContainer.key, (Comparable) hasContainer.value, hasContainer.compare);
			}
		}
		query.limit(queryTemplate.limit).labels(queryTemplate.labels).direction(queryTemplate.direction);
		return query;
	}
}

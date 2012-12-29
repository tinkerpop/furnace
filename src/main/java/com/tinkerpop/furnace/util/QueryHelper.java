package com.tinkerpop.furnace.util;

import com.tinkerpop.blueprints.Query;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class QueryHelper {

    public static Query createQuery(final Vertex vertex, final QueryTemplate query) {
        final Query q = vertex.query();
        for (final QueryTemplate.HasContainer hasContainer : query.hasContainers) {
            if (hasContainer.compare.equals(Query.Compare.EQUAL))
                q.has(hasContainer.key, hasContainer.value);
            else
                q.has(hasContainer.key, (Comparable) hasContainer.value, hasContainer.compare);
        }
        q.limit(query.limit).labels(query.labels).direction(query.direction);
        return q;
    }
}

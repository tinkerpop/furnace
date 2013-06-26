package com.tinkerpop.furnace.util;

import com.tinkerpop.blueprints.CompareRelation;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Query;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.blueprints.util.DefaultVertexQuery;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class VertexQueryBuilder extends DefaultVertexQuery {

    public VertexQueryBuilder() {
        super(null);
    }

    public VertexQueryBuilder has(final String key) {
        super.has(key);
        return this;
    }

    public VertexQueryBuilder hasNot(final String key) {
        super.hasNot(key);
        return this;
    }

    public VertexQueryBuilder has(final String key, final Object value) {
        super.has(key, value);
        return this;
    }

    public VertexQueryBuilder hasNot(final String key, final Object value) {
        super.hasNot(key, value);
        return this;
    }

    @Deprecated
    public <T extends Comparable<T>> VertexQueryBuilder has(final String key, final T value, final Query.Compare compare) {
        return this.has(key, compare, value);
    }

    public VertexQueryBuilder has(final String key, final CompareRelation compare, final Object... values) {
        super.has(key, compare, values);
        return this;
    }

    public <T extends Comparable<T>> VertexQueryBuilder interval(final String key, final T startValue, final T endValue) {
        super.interval(key, startValue, endValue);
        return this;
    }

    public VertexQueryBuilder direction(final Direction direction) {
        super.direction(direction);
        return this;
    }

    public VertexQueryBuilder labels(final String... labels) {
        super.labels(labels);
        return this;
    }

    public VertexQueryBuilder limit(final long limit) {
        super.limit(limit);
        return this;
    }

    public Iterable<Edge> edges() {
        throw new UnsupportedOperationException();
    }

    public Iterable<Vertex> vertices() {
        throw new UnsupportedOperationException();
    }

    public Object vertexIds() {
        throw new UnsupportedOperationException();
    }

    public long count() {
        throw new UnsupportedOperationException();
    }

    public VertexQuery build(final Vertex vertex) {
        VertexQuery query = vertex.query();
        for (final HasContainer hasContainer : this.hasContainers) {
            if (hasContainer.compare.equals(com.tinkerpop.blueprints.Compare.EQUAL)) {
                query = query.has(hasContainer.key, hasContainer.values);
            } else {
                query = query.has(hasContainer.key, hasContainer.compare, hasContainer.values);
            }
        }
        return query.limit(this.limit).labels(this.labels).direction(this.direction);
    }
}

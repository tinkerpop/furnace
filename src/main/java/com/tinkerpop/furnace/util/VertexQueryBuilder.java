package com.tinkerpop.furnace.util;

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

    public VertexQueryBuilder hasNot(final String key, final Object... values) {
        super.hasNot(key, values);
        return this;
    }

    public VertexQueryBuilder has(final String key, final Object... values) {
        super.has(key, values);
        return this;
    }

    @Deprecated
    public <T extends Comparable<T>> VertexQueryBuilder has(final String key, final T value, final Query.Compare compare) {
        return this.has(key, compare, value);
    }

    public <T extends Comparable<T>> VertexQueryBuilder has(final String key, final Query.Compare compare, final T value) {
        super.has(key, compare, value);
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

    public VertexQueryBuilder limit(final long take) {
        super.limit(take);
        return this;
    }

    public VertexQueryBuilder limit(final long skip, final long take) {
        super.limit(skip, take);
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
        final VertexQuery query = vertex.query();
        for (final HasContainer hasContainer : this.hasContainers) {
            if (hasContainer.compare.equals(Query.Compare.EQUAL)) {
                query.has(hasContainer.key, hasContainer.values);
            } else {
                query.has(hasContainer.key, hasContainer.compare, hasContainer.values);
            }
        }
        return query.limit(this.minimum, this.maximum).labels(this.labels).direction(this.direction);
    }
}

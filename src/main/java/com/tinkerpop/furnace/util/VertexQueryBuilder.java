package com.tinkerpop.furnace.util;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Query;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class VertexQueryBuilder implements VertexQuery {

    private static final String[] EMPTY_LABELS = new String[]{};

    public Direction direction = Direction.BOTH;
    public String[] labels = EMPTY_LABELS;
    public long limit = Long.MAX_VALUE;
    public List<HasContainer> hasContainers = new ArrayList<HasContainer>();

    public VertexQueryBuilder has(final String key, final Object value) {
        this.hasContainers.add(new HasContainer(key, value, Query.Compare.EQUAL));
        return this;
    }

    @Deprecated
    public <T extends Comparable<T>> VertexQueryBuilder has(final String key, final T value, final Query.Compare compare) {
        return this.has(key, compare, value);
    }

    public <T extends Comparable<T>> VertexQueryBuilder has(final String key, final Query.Compare compare, final T value) {
        this.hasContainers.add(new HasContainer(key, value, compare));
        return this;
    }

    public <T extends Comparable<T>> VertexQueryBuilder interval(final String key, final T startValue, final T endValue) {
        this.hasContainers.add(new HasContainer(key, startValue, Query.Compare.GREATER_THAN_EQUAL));
        this.hasContainers.add(new HasContainer(key, endValue, Query.Compare.LESS_THAN));
        return this;
    }

    public VertexQueryBuilder direction(final Direction direction) {
        this.direction = direction;
        return this;
    }

    public VertexQueryBuilder labels(final String... labels) {
        this.labels = labels;
        return this;
    }

    public VertexQueryBuilder limit(final long max) {
        this.limit = max;
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

    public class HasContainer {
        public String key;
        public Object value;
        public Query.Compare compare;

        public HasContainer(final String key, final Object value, final Query.Compare compare) {
            this.key = key;
            this.value = value;
            this.compare = compare;
        }
    }

    public VertexQuery build(final Vertex vertex) {
        final VertexQuery query = vertex.query();
        for (final HasContainer hasContainer : this.hasContainers) {
            if (hasContainer.compare.equals(Query.Compare.EQUAL)) {
                query.has(hasContainer.key, hasContainer.value);
            } else {
                query.has(hasContainer.key, hasContainer.compare, (Comparable) hasContainer.value);
            }
        }
        return query.limit(this.limit).labels(this.labels).direction(this.direction);
    }

}

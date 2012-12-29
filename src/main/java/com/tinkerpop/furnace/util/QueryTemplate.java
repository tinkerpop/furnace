package com.tinkerpop.furnace.util;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class QueryTemplate {

    private static final String[] EMPTY_LABELS = new String[]{};

    public Direction direction = Direction.BOTH;
    public String[] labels = EMPTY_LABELS;
    public long limit = Long.MAX_VALUE;
    public List<HasContainer> hasContainers = new ArrayList<HasContainer>();

    public QueryTemplate has(final String key, final Object value) {
        this.hasContainers.add(new HasContainer(key, value, Query.Compare.EQUAL));
        return this;
    }

    public <T extends Comparable<T>> QueryTemplate has(final String key, final T value, final Query.Compare compare) {
        this.hasContainers.add(new HasContainer(key, value, compare));
        return this;
    }

    public <T extends Comparable<T>> QueryTemplate interval(final String key, final T startValue, final T endValue) {
        this.hasContainers.add(new HasContainer(key, startValue, Query.Compare.GREATER_THAN_EQUAL));
        this.hasContainers.add(new HasContainer(key, endValue, Query.Compare.LESS_THAN));
        return this;
    }

    public QueryTemplate direction(final Direction direction) {
        this.direction = direction;
        return this;
    }

    public QueryTemplate labels(final String... labels) {
        this.labels = labels;
        return this;
    }

    public QueryTemplate limit(final long max) {
        this.limit = max;
        return this;
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

}

package com.tinkerpop.furnace.wrappers.derived;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface Derivation {
    public Iterable<Vertex> adjacent(Direction direction, Vertex vertex);

    public Iterable<Edge> incident(Direction direction, Vertex vertex);
}

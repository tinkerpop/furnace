package com.tinkerpop.furnace.wrappers;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface Derivation {

    public Iterable<Vertex> adjacent(Vertex vertex);
}

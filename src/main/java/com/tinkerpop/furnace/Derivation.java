package com.tinkerpop.furnace;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface Derivation {

    public Iterable<Edge> outEdges(Vertex vertex);

    public Iterable<Edge> inEdges(Vertex vertex);
}

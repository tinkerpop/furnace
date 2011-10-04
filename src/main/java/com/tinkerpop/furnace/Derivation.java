package com.tinkerpop.furnace;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface Derivation {

    public Iterable<Edge> outEdges(Vertex vertex);

    public Iterable<Edge> inEdges(Vertex vertex);
}

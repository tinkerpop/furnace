package com.tinkerpop.furnace.wrappers;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedEdge extends DerivedElement implements Edge {

    public DerivedEdge(final Edge rawEdge, final DerivedGraph derivedGraph) {
        super(rawEdge, derivedGraph);
    }

    public String getLabel() {
        return ((Edge) this.rawElement).getLabel();
    }

    public Vertex getVertex(final Direction direction) {
        return new DerivedVertex(((Edge) this.rawElement).getVertex(direction), this.derivedGraph);
    }

}

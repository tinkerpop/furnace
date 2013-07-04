package com.tinkerpop.furnace.wrappers.derived;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedEdge extends DerivedElement implements Edge {

    public DerivedEdge(final Edge rawEdge, final DerivedGraph derivedGraph) {
        super(rawEdge, derivedGraph);
    }

    public String getLabel() {
        return ((Edge) this.baseElement).getLabel();
    }

    public Vertex getVertex(final Direction direction) {
        return new DerivedVertex(((Edge) this.baseElement).getVertex(direction), this.derivedGraph);
    }

    public Edge getBaseEdge() {
        return (Edge) this.baseElement;
    }

    public static Edge generateDerivedEdge(final Vertex outVertex, final Vertex inVertex, final String label, final DerivedGraph derivedGraph) {
        return new DerivedEdge(new Edge() {

            private final Map<String, Object> properties = new HashMap<String, Object>();

            @Override
            public Vertex getVertex(Direction direction) throws IllegalArgumentException {
                return direction.equals(Direction.OUT) ?
                        outVertex instanceof DerivedVertex ? ((DerivedVertex) outVertex).getBaseVertex() : outVertex :
                        inVertex instanceof DerivedVertex ? ((DerivedVertex) inVertex).getBaseVertex() : inVertex;
            }

            @Override
            public String getLabel() {
                return label;
            }

            @Override
            public <T> T getProperty(final String key) {
                return (T) this.properties.get(key);
            }

            @Override
            public Set<String> getPropertyKeys() {
                return this.properties.keySet();
            }

            @Override
            public void setProperty(final String key, final Object value) {
                this.properties.put(key, value);
            }

            @Override
            public <T> T removeProperty(final String key) {
                return (T) this.properties.remove(key);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("This edge does not exist as it is a derived edge");
            }

            @Override
            public Object getId() {
                return 0;
            }
        }, derivedGraph);
    }
}

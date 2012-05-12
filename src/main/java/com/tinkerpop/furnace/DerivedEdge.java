package com.tinkerpop.furnace;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.blueprints.pgm.util.StringFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedEdge implements Edge {

    private final Vertex inVertex;
    private final Vertex outVertex;
    private final String label;
    private Map<String, Object> properties;

    private final static Set<String> EMPTY_SET = new HashSet<String>();

    public DerivedEdge(final Vertex outVertex, final Vertex inVertex, final String label) {
        this.outVertex = outVertex;
        this.inVertex = inVertex;
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public Vertex getOutVertex() {
        return this.outVertex;
    }

    public Vertex getInVertex() {
        return this.inVertex;
    }

    public Set<String> getPropertyKeys() {
        if (null == this.properties)
            return EMPTY_SET;
        else
            return this.properties.keySet();
    }

    public Object getId() {
        return null;
    }

    public Object removeProperty(final String key) {
        if (null == this.properties)
            return null;
        else
            return this.properties.remove(key);
    }

    public void setProperty(final String key, final Object value) {
        if (null == this.properties)
            this.properties = new HashMap<String, Object>();
        else
            this.properties.put(key, value);
    }

    public Object getProperty(final String key) {
        if (null == this.properties)
            return null;
        else
            return this.properties.get(key);
    }

    public String toString() {
        return StringFactory.edgeString(this);
    }
}

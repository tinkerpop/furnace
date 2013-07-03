package com.tinkerpop.furnace.wrappers;

import com.tinkerpop.blueprints.Element;

import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedElement implements Element {

    protected final DerivedGraph derivedGraph;
    protected final Element rawElement;

    public DerivedElement(final Element rawElement, final DerivedGraph derivedGraph) {
        this.rawElement = rawElement;
        this.derivedGraph = derivedGraph;
    }

    public <T> T getProperty(final String key) {
        return this.rawElement.getProperty(key);
    }

    public <T> T removeProperty(final String key) {
        return this.rawElement.removeProperty(key);
    }

    public void setProperty(final String key, final Object value) {
        this.rawElement.setProperty(key, value);
    }

    public Set<String> getPropertyKeys() {
        return this.rawElement.getPropertyKeys();
    }

    public void remove() {
        this.rawElement.remove();
    }

    public Object getId() {
        return this.rawElement.getId();
    }

    public boolean equals(Object object) {
        return (object.getClass().equals(this.rawElement.getClass()) && ((Element) object).getId().equals(this.getId()));
    }

    public String toString() {
        return this.rawElement.toString();
    }

    public int hashCode() {
        return this.rawElement.hashCode();
    }
}

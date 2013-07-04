package com.tinkerpop.furnace.wrappers.derived;

import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.util.wrappers.wrapped.WrappedElement;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedElement extends WrappedElement implements Element {

    protected final DerivedGraph derivedGraph;

    public DerivedElement(final Element rawElement, final DerivedGraph derivedGraph) {
        super(rawElement);
        this.derivedGraph = derivedGraph;
    }
}

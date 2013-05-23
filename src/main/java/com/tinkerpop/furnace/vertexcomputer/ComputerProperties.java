package com.tinkerpop.furnace.vertexcomputer;

import com.tinkerpop.blueprints.Vertex;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface ComputerProperties {

    public void setProperty(final Vertex vertex, final String key, final Object value);

    public <T> T getProperty(final Vertex vertex, final String key);

    public <T> T removeProperty(final Vertex vertex, final String key);
}

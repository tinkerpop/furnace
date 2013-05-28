package com.tinkerpop.furnace.computer;

import com.tinkerpop.blueprints.Vertex;

/**
 * LocalMemory denotes the vertex properties that are used for the graph computation.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface LocalMemory {

    public void setProperty(Vertex vertex, String key, Object value);

    public <T> T getProperty(Vertex vertex, String key);

    public <T> T removeProperty(Vertex vertex, String key);

    public void setComputeKeys(String... keys);

    public void setFinalComputeKeys(String... keys);

    public boolean isComputeKey(String key);

    public void completeRound();

    public void setIsolation(Isolation isolation);
}

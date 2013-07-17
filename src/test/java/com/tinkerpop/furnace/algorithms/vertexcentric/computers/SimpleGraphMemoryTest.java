package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
public class SimpleGraphMemoryTest {
    @Test
    public void shouldIncrement() {
        final SimpleGraphMemory memory = new SimpleGraphMemory();

        Assert.assertEquals(1l, memory.increment("test-1", 1));
        Assert.assertEquals(20l, memory.increment("test-2", 20));
        Assert.assertEquals(2l, memory.increment("test-1", 1));
        Assert.assertEquals(60l, memory.increment("test-2", 40));
    }

    @Test
    public void shouldDecrement() {
        final SimpleGraphMemory memory = new SimpleGraphMemory();

        Assert.assertEquals(1l, memory.increment("test-1", 1));
        Assert.assertEquals(20l, memory.increment("test-2", 20));
        Assert.assertEquals(0l, memory.decrement("test-1", 1));
        Assert.assertEquals(-20l, memory.decrement("test-2", 40));
    }
}

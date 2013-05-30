package com.tinkerpop.furnace.computer.memory;

import com.tinkerpop.furnace.computer.Isolation;
import com.tinkerpop.furnace.computer.LocalMemory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class AbstractLocalMemory implements LocalMemory {

    protected List<String> computeKeys = new ArrayList<String>();
    protected List<String> finalComputeKeys = new ArrayList<String>();
    protected Isolation isolation;
    protected boolean phase = true;

    public void setComputeKeys(final String... keys) {
        this.computeKeys = Arrays.asList(keys);
    }

    public void setFinalComputeKeys(final String... keys) {
        this.finalComputeKeys = Arrays.asList(keys);
    }

    public void setIsolation(final Isolation isolation) {
        this.isolation = isolation;
    }

    public boolean isComputeKey(final String key) {
        return this.computeKeys.contains(key) || this.finalComputeKeys.contains(key);
    }

    public void completeIteration() {
        this.phase = !this.phase;
    }

    protected String generateGetKey(final String key) {
        if (this.finalComputeKeys.contains(key))
            return key;

        if (isolation.equals(Isolation.BSP))
            return key + !phase;
        else
            return key;
    }

    protected String generateSetKey(final String key) {
        if (this.finalComputeKeys.contains(key))
            return key;

        if (isolation.equals(Isolation.BSP))
            return key + phase;
        else
            return key;
    }
}

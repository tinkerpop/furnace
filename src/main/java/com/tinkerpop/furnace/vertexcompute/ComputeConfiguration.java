package com.tinkerpop.furnace.vertexcompute;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * (c) Matthias Broecheler (me@matthiasb.com)
 */

public class ComputeConfiguration {


    public enum Isolation {
        /**
         * Computations are carried out in Bulk synchronous: The computation is completed on all
         * vertices and all resulting changes are buffered until such completion before the changes
         * become visible. Only after all computations are completed is a round complete.
         */
        BSP,
        /**
         * As with BSP but mutations may become visible before the end of the round.
         */
        DIRTY_BSP
    }

    private final Isolation isolation;
    private final String computeProperty;
    private final Set<String> pinnedStateKeys;

    public ComputeConfiguration(Isolation isolation, String computeProperty, Set<String> pinnedStateKeys) {
        Preconditions.checkNotNull(isolation);
        Preconditions.checkNotNull(pinnedStateKeys);
        this.computeProperty = computeProperty;
        this.pinnedStateKeys = ImmutableSet.copyOf(pinnedStateKeys);
        this.isolation = isolation;

    }

    public Isolation getIsolation() {
        return this.isolation;
    }

    /**
     * Key which is used to access the computed value for a vertex
     * @return
     */
    public String getComputeProperty() {
        return computeProperty;
    }

    public boolean isPinnedStateKey(String key) {
        return pinnedStateKeys.contains(key);
    }

}

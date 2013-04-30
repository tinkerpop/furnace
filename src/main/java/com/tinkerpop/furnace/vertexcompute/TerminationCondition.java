package com.tinkerpop.furnace.vertexcompute;

/**
 * (c) Matthias Broecheler (me@matthiasb.com)
 */

public interface TerminationCondition {

    public boolean terminate(ComputeStatistics statistics, SharedState state);

}

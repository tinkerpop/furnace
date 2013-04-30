package com.tinkerpop.furnace.vertexcompute;

/**
 * (c) Matthias Broecheler (me@matthiasb.com)
 */

public interface ComputeStatistics {

    //TODO: additional job statistics

    public int numIterations();

    public int numFailures();

}

package com.tinkerpop.furnace.vertexcompute;

import com.tinkerpop.blueprints.Graph;

import java.util.Map;

/**
 * (c) Matthias Broecheler (me@matthiasb.com)
 */

public interface VertexComputerGraph {

    public ComputeResult execute(VertexComputer computer, ComputeConfiguration configuration, TerminationCondition termination, Map<String,Object> initialState);

    public ComputeResult execute(VertexComputer computer, ComputeConfiguration configuration, TerminationCondition termination);

}

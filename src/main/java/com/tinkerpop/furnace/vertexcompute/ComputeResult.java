package com.tinkerpop.furnace.vertexcompute;

import com.tinkerpop.blueprints.Vertex;

import java.util.Map;

/**
 * (c) Matthias Broecheler (me@matthiasb.com)
 */

public interface ComputeResult {

    public ComputeStatistics getFinalStatistics();

    //Retrieve the result from the computation

    public Map<String,Object> getFinalState();

    public <R> R getResult(Object vertexId);

    public <R> R getResult(Vertex v);

    public <R> Iterable<Pair<R>> getResults();

    public interface Pair<R> {

        public Object getVertexId();

        public R getResult();

    }

}

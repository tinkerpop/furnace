package com.tinkerpop.furnace.vertexcomputer;

import java.util.List;

/**
 * A GraphComputer is the global coordinator of the distributed graph computation.
 * GraphComputer is responsible for constructing the VertexComputer and maintaining the SharedState between them.
 * GraphComputer is the means by which the results of the distributed computation are accessed.
 * Finally, GraphComputer provide the requisite cleanup method for removing all computational side-effects within the graph.
 *
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public abstract class GraphComputer {

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

    protected VertexComputer vertexComputer;
    protected Isolation isolation;
    protected SharedState sharedState;
    protected ComputerProperties computerProperties;
    protected List<String> computeKeys;

    public VertexComputer getVertexComputer() {
        return vertexComputer;
    }

    public Isolation getIsolation() {
        return this.isolation;
    }

    public SharedState getSharedState() {
        return this.sharedState;
    }

    public ComputerProperties getComputerProperties() {
        return this.computerProperties;
    }

    public boolean isComputeKey(final String key) {
        return this.computeKeys.contains(key);
    }

    /**
     * Whether or not the VertexComputers need to be setup or if this stage of the computation can be skipped.
     *
     * @return whether or not do execute the setup stage of the computation
     */
    public boolean doSetup() {
        return true;
    }

    public static <R extends GraphComputerBuilder> R create() {
        return null;
    }

    public abstract boolean terminate();
}

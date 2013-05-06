package com.tinkerpop.furnace.vertexcompute;

import com.google.common.base.Preconditions;

/**
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

    private final VertexComputer vertexComputer;
    private final Isolation isolation;
    private final SharedState sharedState;

    public GraphComputer(final VertexComputer vertexComputer, final SharedState state, final Isolation isolation) {
        Preconditions.checkNotNull(vertexComputer);
        Preconditions.checkNotNull(state);
        Preconditions.checkNotNull(isolation);
        this.vertexComputer = vertexComputer;
        this.sharedState = state;
        this.isolation = isolation;
    }

    public VertexComputer getVertexComputer() {
        return vertexComputer;
    }

    public Isolation getIsolation() {
        return this.isolation;
    }

    public SharedState getSharedState() {
        return this.sharedState;
    }

    public boolean doSetup() {
        return true;
    }

    // public abstract roundComplete() ?? What about ASP. Is terminate sufficient to understand the semantics of a "round" ?

    public static <R extends GraphComputerBuilder> R create() {
        return null;
    }

    public abstract boolean terminate();

    public abstract ComputeResult generateResult();

}

package com.tinkerpop.furnace.vertexcompute;

import com.google.common.base.Preconditions;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class GraphComputerBuilder {

    protected GraphComputer.Isolation isolation = GraphComputer.Isolation.BSP;
    protected SharedState sharedState;

    public <R extends GraphComputerBuilder> R isolation(final GraphComputer.Isolation isolation) {
        this.isolation = isolation;
        return (R) this;
    }

    public <R extends GraphComputerBuilder> R sharedState(final SharedState sharedState) {
        this.sharedState = sharedState;
        return (R) this;
    }

    public <R extends GraphComputer> R build() {
        Preconditions.checkNotNull(this.isolation);
        Preconditions.checkNotNull(this.sharedState);
        return null;
    }

}

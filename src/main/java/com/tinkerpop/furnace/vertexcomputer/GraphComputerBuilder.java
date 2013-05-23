package com.tinkerpop.furnace.vertexcomputer;

import com.google.common.base.Preconditions;

/**
 * A GraphComputer typically requires numerous parameterizations.
 * To simplify GraphComputer construction, every GraphComputer must come with a GraphComputerBuilder.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class GraphComputerBuilder {

    protected GraphComputer.Isolation isolation = GraphComputer.Isolation.BSP;
    protected SharedState sharedState;
    protected ComputerProperties computerProperties;
    protected String[] computeKeys;


    public <R extends GraphComputerBuilder> R isolation(final GraphComputer.Isolation isolation) {
        this.isolation = isolation;
        return (R) this;
    }

    public <R extends GraphComputerBuilder> R sharedState(final SharedState sharedState) {
        this.sharedState = sharedState;
        return (R) this;
    }

    public <R extends GraphComputerBuilder> R computerProperties(final ComputerProperties computerProperties) {
        this.computerProperties = computerProperties;
        return (R) this;
    }

    public <R extends GraphComputerBuilder> R computeKeys(final String... keys) {
        this.computeKeys = keys;
        return (R) this;
    }

    public <R extends GraphComputer> R build() {
        Preconditions.checkNotNull(this.isolation);
        Preconditions.checkNotNull(this.sharedState);
        Preconditions.checkNotNull(this.computerProperties);
        Preconditions.checkNotNull(this.computeKeys);
        return null;
    }
}

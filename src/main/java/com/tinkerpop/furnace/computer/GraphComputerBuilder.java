package com.tinkerpop.furnace.computer;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Graph;

/**
 * A GraphComputer typically requires numerous parameterizations.
 * To simplify GraphComputer construction, every GraphComputer must come with a GraphComputerBuilder.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class GraphComputerBuilder {

    protected Isolation isolation = Isolation.BSP;
    protected GlobalMemory globalMemory;
    protected LocalMemory localMemory;
    protected Evaluator evaluator;
    protected Graph graph;

    public <R extends GraphComputerBuilder> R isolation(final Isolation isolation) {
        this.isolation = isolation;
        return (R) this;
    }

    public <R extends GraphComputerBuilder> R globalMemory(final GlobalMemory globalMemory) {
        this.globalMemory = globalMemory;
        return (R) this;
    }

    public <R extends GraphComputerBuilder> R localMemory(final LocalMemory localMemory) {
        this.localMemory = localMemory;
        return (R) this;
    }

    public <R extends GraphComputerBuilder> R evaluator(final Evaluator evaluator) {
        this.evaluator = evaluator;
        return (R) this;
    }

    public <R extends GraphComputerBuilder> R graph(final Graph graph) {
        this.graph = graph;
        return (R) this;
    }

    public <R extends GraphComputer> R build() {
        Preconditions.checkNotNull(this.isolation);
        Preconditions.checkNotNull(this.globalMemory);
        Preconditions.checkNotNull(this.localMemory);
        Preconditions.checkNotNull(this.evaluator);
        return null;
    }
}

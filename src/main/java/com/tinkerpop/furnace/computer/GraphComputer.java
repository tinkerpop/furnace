package com.tinkerpop.furnace.computer;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Graph;

public abstract class GraphComputer {

    protected Graph graph;
    protected VertexProgram vertexProgram;
    protected Isolation isolation;
    protected GlobalMemory globalMemory;
    protected LocalMemory localMemory;
    protected Evaluator evaluator;

    public Graph getGraph() {
        return this.graph;
    }

    public VertexProgram getVertexProgram() {
        return this.vertexProgram;
    }

    public Isolation getIsolation() {
        return this.isolation;
    }

    public GlobalMemory getGlobalMemory() {
        return this.globalMemory;
    }

    public LocalMemory getLocalMemory() {
        return this.localMemory;
    }

    public void execute() {
        Preconditions.checkNotNull(this.isolation);
        Preconditions.checkNotNull(this.globalMemory);
        Preconditions.checkNotNull(this.localMemory);
        Preconditions.checkNotNull(this.evaluator);
        this.evaluator.execute(this);
    }

    public boolean doSetup() {
        return true;
    }

    public static <R extends GraphComputerBuilder> R create() {
        return null;
    }

    public abstract boolean terminate();
}

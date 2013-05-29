package com.tinkerpop.furnace.computer;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Graph;

/**
 * A GraphComputer maintains reference to the components requisite for the execution of a vertex-centric graph computation.
 * In this model of computing, each vertex maintains a "program."
 * Each vertex program is embedded in a communication topology as defined by the explicit graph structure.
 * A vertex program can read/write its local properties, but can only read its adjacent neighbor's properties.
 * In this manner, a pull-based communication model is defined where a vertex can only pull information from its adjacents.
 * Given the information gathered from the adjacents and the rules of the vertex program, the local properties are updated.
 * The collective behavior of all vertex programs against the global and local memory structures computation's results.
 *
 * @author Matthias Broecheler (me@matthiasb.com)
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
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

    public Evaluator getEvaluator() {
        return this.evaluator;
    }

    public void execute() {
        Preconditions.checkNotNull(this.isolation);
        Preconditions.checkNotNull(this.globalMemory);
        Preconditions.checkNotNull(this.localMemory);
        Preconditions.checkNotNull(this.evaluator);
        this.evaluator.execute(this);
    }

    public boolean doSetupIteration() {
        return true;
    }

    public static <R extends GraphComputerBuilder> R create() {
        return null;
    }

    public abstract boolean terminate();
}

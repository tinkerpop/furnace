package com.tinkerpop.furnace.vertexcompute.wrappers;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.WrapperGraph;
import com.tinkerpop.furnace.vertexcompute.ComputeResult;
import com.tinkerpop.furnace.vertexcompute.GraphComputer;
import com.tinkerpop.furnace.vertexcompute.VertexComputer;
import com.tinkerpop.furnace.vertexcompute.VertexComputerGraph;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SerialVertexComputerGraph<T extends Graph> implements VertexComputerGraph, WrapperGraph<T> {

    private final T baseGraph;

    public SerialVertexComputerGraph(final T baseGraph) {
        Preconditions.checkArgument(baseGraph.getFeatures().supportsVertexIteration);
        this.baseGraph = baseGraph;
    }

    public T getBaseGraph() {
        return this.baseGraph;
    }

    public ComputeResult execute(final GraphComputer graphComputer) {
        final VertexComputer vertexComputer = graphComputer.getVertexComputer();

        if (graphComputer.doSetup()) {
            for (final Vertex vertex : this.baseGraph.getVertices()) {
                vertexComputer.setup(vertex, graphComputer.getSharedState());
            }
        }

        while (!graphComputer.terminate()) {
            for (final Vertex vertex : this.baseGraph.getVertices()) {
                vertexComputer.compute(vertex, graphComputer.getSharedState());
            }
        }

        return graphComputer.generateResult();
    }

    public void cleanup(final GraphComputer graphComputer) {
        final VertexComputer vertexComputer = graphComputer.getVertexComputer();

        for (final Vertex vertex : this.baseGraph.getVertices()) {
            vertexComputer.cleanup(vertex, graphComputer.getSharedState());
        }
    }
}

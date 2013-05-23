package com.tinkerpop.furnace.vertexcompute.wrappers;

import com.google.common.base.Preconditions;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.WrapperGraph;
import com.tinkerpop.furnace.vertexcompute.ComputerProperties;
import com.tinkerpop.furnace.vertexcompute.GraphComputer;
import com.tinkerpop.furnace.vertexcompute.SharedState;
import com.tinkerpop.furnace.vertexcompute.VertexComputer;
import com.tinkerpop.furnace.vertexcompute.VertexComputerGraph;

/**
 * A SerialVertexComputerGraph implements the vertex-centric graph computing model in a serial, single-threaded manner.
 * This implementation is simple and provides a reference implementation for the semantics of the interfaces.
 *
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

    public ComputerProperties execute(final GraphComputer graphComputer) {
        final VertexComputer vertexComputer = graphComputer.getVertexComputer();
        final SharedState sharedState = graphComputer.getSharedState();

        if (graphComputer.doSetup()) {
            for (final Vertex vertex : this.baseGraph.getVertices()) {
                vertexComputer.setup(new HeavyVertex(vertex, graphComputer), sharedState);
            }
        }

        while (!graphComputer.terminate()) {
            for (final Vertex vertex : this.baseGraph.getVertices()) {
                vertexComputer.compute(new HeavyVertex(vertex, graphComputer), sharedState);
            }
        }

        return graphComputer.getComputerProperties();
    }
}

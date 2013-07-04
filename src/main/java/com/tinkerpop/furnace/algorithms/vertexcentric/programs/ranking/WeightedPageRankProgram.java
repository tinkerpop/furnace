package com.tinkerpop.furnace.algorithms.vertexcentric.programs.ranking;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.EdgeHelper;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.programs.AbstractVertexProgram;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import com.tinkerpop.pipes.PipeFunction;

import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class WeightedPageRankProgram extends AbstractVertexProgram {

    private VertexQueryBuilder outgoingQuery = new VertexQueryBuilder().direction(Direction.OUT);
    private VertexQueryBuilder incomingQuery = new VertexQueryBuilder().direction(Direction.IN);
    private PipeFunction<Edge, Double> edgeWeightFunction = new PipeFunction<Edge, Double>() {
        @Override
        public Double compute(Edge argument) {
            return 1.0d;
        }
    };

    public static final String PAGE_RANK = WeightedPageRankProgram.class.getName() + ".pageRank";
    public static final String EDGE_WEIGHT_SUM = WeightedPageRankProgram.class.getName() + "edgeWeightSum";

    private double vertexCountAsDouble = 1;
    private double alpha = 0.85d;
    private int totalIterations = 30;

    protected WeightedPageRankProgram() {
        computeKeys.put(PAGE_RANK, KeyType.VARIABLE);
        computeKeys.put(EDGE_WEIGHT_SUM, KeyType.CONSTANT);
    }

    public void setup(final GraphMemory graphMemory) {

    }

    public void execute(final Vertex vertex, final GraphMemory graphMemory) {
        if (graphMemory.isInitialIteration()) {
            vertex.setProperty(PAGE_RANK, 1.0d / this.vertexCountAsDouble);
            double edgeWeightSum = 0.0d;
            for (final Edge incident : this.outgoingQuery.build(vertex).edges()) {
                edgeWeightSum = edgeWeightSum + this.edgeWeightFunction.compute(incident);
            }
            vertex.setProperty(EDGE_WEIGHT_SUM, edgeWeightSum);
        } else {
            double newPageRank = 0.0d;
            for (final Edge incident : this.incomingQuery.build(vertex).edges()) {
                final Vertex other = EdgeHelper.getOther(incident, vertex);
                final Double otherPageRank = other.getProperty(PAGE_RANK);
                final Double otherEdgeWeightSum = other.getProperty(EDGE_WEIGHT_SUM);
                newPageRank = newPageRank + (otherPageRank * (this.edgeWeightFunction.compute(incident) / otherEdgeWeightSum));
            }
            vertex.setProperty(PAGE_RANK, (this.alpha * newPageRank) + ((1.0d - this.alpha) / this.vertexCountAsDouble));
        }
    }

    public boolean terminate(final GraphMemory graphMemory) {
        return graphMemory.getIteration() >= this.totalIterations;
    }

    public static PipeFunction<Edge, Double> getEdgeWeightPropertyFunction(final String edgeWeightKey) {
        return new PipeFunction<Edge, Double>() {
            @Override
            public Double compute(final Edge argument) {
                final Number d = argument.getProperty(edgeWeightKey);
                return (null == d) ? 0.0d : d.doubleValue();
            }
        };
    }

    public static PipeFunction<Edge, Double> getEdgeLabelBiasFunction(final Map<String, Double> edgeLabelBiasMap) {
        return new PipeFunction<Edge, Double>() {
            @Override
            public Double compute(final Edge argument) {
                final Double d = edgeLabelBiasMap.get(argument.getLabel());
                return (null == d) ? 0.0d : d;
            }
        };
    }

    public static Builder create() {
        return new Builder();
    }

    //////////////////////////////

    public static class Builder {

        private final WeightedPageRankProgram vertexProgram = new WeightedPageRankProgram();

        public Builder iterations(final int iterations) {
            this.vertexProgram.totalIterations = iterations;
            return this;
        }

        public Builder alpha(final double alpha) {
            this.vertexProgram.alpha = alpha;
            return this;
        }

        public Builder outgoing(final VertexQueryBuilder outgoingQuery) {
            this.vertexProgram.outgoingQuery = outgoingQuery;
            return this;
        }

        public Builder incoming(final VertexQueryBuilder incomingQuery) {
            this.vertexProgram.incomingQuery = incomingQuery;
            return this;
        }

        public Builder edgeWeightFunction(final PipeFunction<Edge, Double> edgeWeightFunction) {
            this.vertexProgram.edgeWeightFunction = edgeWeightFunction;
            return this;
        }

        public Builder vertexCount(final int count) {
            this.vertexProgram.vertexCountAsDouble = (double) count;
            return this;
        }

        public WeightedPageRankProgram build() {
            return this.vertexProgram;
        }
    }

}

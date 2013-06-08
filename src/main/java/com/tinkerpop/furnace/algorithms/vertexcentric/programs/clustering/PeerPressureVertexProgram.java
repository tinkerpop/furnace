package com.tinkerpop.furnace.algorithms.vertexcentric.programs.clustering;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.programs.AbstractVertexProgram;
import com.tinkerpop.furnace.util.VertexQueryBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PeerPressureVertexProgram extends AbstractVertexProgram {

    public static final String CLUSTER = "cluster";
    public static final String EDGE_COUNT = "edgeCount";

    private int totalIterations = 30;

    protected PeerPressureVertexProgram() {
        computeKeys.put(CLUSTER, KeyType.VARIABLE);
        computeKeys.put(EDGE_COUNT, KeyType.CONSTANT);
    }

    public void execute(final Vertex vertex, final GraphMemory globalMemory) {
        if (globalMemory.isInitialIteration()) {
            vertex.setProperty(CLUSTER, vertex.getId());
            vertex.setProperty(EDGE_COUNT, (double) this.outgoingQuery.build(vertex).count());
        } else {

            final Map<Object, Double> votes = new HashMap<Object, Double>();
            // tally up the votes
            for (final Vertex adjacent : this.incomingQuery.build(vertex).vertices()) {
                incrementVote(votes, adjacent.getProperty(CLUSTER), 1.0d / (Double) adjacent.getProperty(EDGE_COUNT));
            }
            // find the largest vote
            Object finalVoteCluster = vertex.getProperty(CLUSTER);
            Double finalVoteScore = Double.MIN_VALUE;
            for (Map.Entry<Object, Double> entry : votes.entrySet()) {
                if (entry.getValue() > finalVoteScore) {
                    finalVoteCluster = entry.getKey();
                    finalVoteScore = entry.getValue();
                } else if (entry.getValue().equals(finalVoteScore)) {
                    // tie breaker select based on key
                    if (finalVoteCluster instanceof Comparable && ((Comparable) finalVoteCluster).compareTo(entry.getKey()) < 0) {
                        finalVoteCluster = entry.getKey();
                        finalVoteScore = entry.getValue();
                    }
                }
            }
            // assign vertex to cluster based on strongest vote
            vertex.setProperty(CLUSTER, finalVoteCluster);
        }
    }

    private void incrementVote(final Map<Object, Double> votes, final Object vote, final Double increment) {
        Double current = votes.get(vote);
        if (current == null)
            current = 0.0d;
        current = current + increment;
        votes.put(vote, current);
    }

    public boolean terminate(final GraphMemory graphMemory) {
        return graphMemory.getIteration() >= this.totalIterations;
    }

    public static Builder create() {
        return new Builder();
    }

    //////////////////////////////

    public static class Builder {

        private final PeerPressureVertexProgram vertexProgram = new PeerPressureVertexProgram();

        public Builder iterations(final int iterations) {
            this.vertexProgram.totalIterations = iterations;
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

        public PeerPressureVertexProgram build() {
            return this.vertexProgram;
        }
    }
}
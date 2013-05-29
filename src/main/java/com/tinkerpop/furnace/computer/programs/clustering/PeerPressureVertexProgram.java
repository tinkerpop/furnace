package com.tinkerpop.furnace.computer.programs.clustering;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.computer.GlobalMemory;
import com.tinkerpop.furnace.computer.VertexProgram;
import com.tinkerpop.furnace.util.VertexQueryBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PeerPressureVertexProgram implements VertexProgram {

    private final VertexQueryBuilder outgoingQuery;
    private final VertexQueryBuilder incomingQuery;

    public PeerPressureVertexProgram(final VertexQueryBuilder outgoingQuery, final VertexQueryBuilder incomingQuery) {
        this.outgoingQuery = outgoingQuery;
        this.incomingQuery = incomingQuery;
    }


    public void setup(final Vertex vertex, final GlobalMemory globalMemory) {
        vertex.setProperty(PeerPressureGraphComputer.CLUSTER, vertex.getId());
        vertex.setProperty(PeerPressureGraphComputer.EDGE_COUNT, (double) this.outgoingQuery.build(vertex).count());
    }

    public void execute(final Vertex vertex, final GlobalMemory globalMemory) {
        final Map<Object, Double> votes = new HashMap<Object, Double>();
        // tally up the votes
        for (final Vertex adjacent : this.incomingQuery.build(vertex).vertices()) {
            incrementVote(votes, adjacent.getProperty(PeerPressureGraphComputer.CLUSTER), 1.0d / (Double) adjacent.getProperty(PeerPressureGraphComputer.EDGE_COUNT));
        }
        // find the largest vote
        Object finalVoteCluster = vertex.getProperty(PeerPressureGraphComputer.CLUSTER);
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
        vertex.setProperty(PeerPressureGraphComputer.CLUSTER, finalVoteCluster);
    }

    private void incrementVote(final Map<Object, Double> votes, final Object vote, final Double increment) {
        Double current = votes.get(vote);
        if (current == null)
            current = 0.0d;
        current = current + increment;
        votes.put(vote, current);
    }
}

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

    private final VertexQueryBuilder voteStrengthQuery;
    private final VertexQueryBuilder incomingVotesQuery;

    public PeerPressureVertexProgram(final VertexQueryBuilder voteStrengthQuery, final VertexQueryBuilder incomingVotesQuery) {
        this.voteStrengthQuery = voteStrengthQuery;
        this.incomingVotesQuery = incomingVotesQuery;
    }


    public void setup(final Vertex vertex, final GlobalMemory globalMemory) {
        vertex.setProperty(PeerPressureGraphComputer.CLUSTER, vertex.getId());
        vertex.setProperty(PeerPressureGraphComputer.VOTE_STRENGTH, (double) this.voteStrengthQuery.build(vertex).count());
    }

    public void execute(final Vertex vertex, final GlobalMemory globalMemory) {
        final Map<Object, Double> votes = new HashMap<Object, Double>();
        for (Vertex adjacent : this.incomingVotesQuery.build(vertex).vertices()) {
            votes.put(adjacent.getProperty(PeerPressureGraphComputer.CLUSTER), 1.0d / (Double) adjacent.getProperty(PeerPressureGraphComputer.VOTE_STRENGTH));
        }

        Object finalVoteCluster = vertex.getProperty(PeerPressureGraphComputer.CLUSTER);
        Double finalVoteScore = Double.MIN_VALUE;
        for (Map.Entry<Object, Double> entry : votes.entrySet()) {
            if (entry.getValue() > finalVoteScore) {
                finalVoteCluster = entry.getKey();
                finalVoteScore = entry.getValue();
            }
        }
        vertex.setProperty(PeerPressureGraphComputer.CLUSTER, finalVoteCluster);
    }
}

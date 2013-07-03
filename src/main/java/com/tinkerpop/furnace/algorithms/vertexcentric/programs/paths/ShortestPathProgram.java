package com.tinkerpop.furnace.algorithms.vertexcentric.programs.paths;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphMemory;
import com.tinkerpop.furnace.algorithms.vertexcentric.programs.AbstractVertexProgram;
import com.tinkerpop.furnace.util.VertexQueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ShortestPathProgram extends AbstractVertexProgram {

    private VertexQueryBuilder incomingQuery;
    private int diameter = 10;

    public static final String DEAD_PATHS = ShortestPathProgram.class.getName() + ".deadPaths";
    public static final String LIVE_PATHS = ShortestPathProgram.class.getName() + ".livePaths";


    public void execute(final Vertex vertex, final GraphMemory graphMemory) {
        if (graphMemory.isInitialIteration()) {
            final List<List<Object>> paths = new ArrayList<List<Object>>();
            final List<Object> path = new ArrayList<Object>();
            path.add(vertex.getId());
            paths.add(path);
            vertex.setProperty(LIVE_PATHS, paths);
            vertex.setProperty(DEAD_PATHS, new HashMap<Object, List<Vertex>>());
        } else {

            final Map<Object, List<Object>> deadPaths = vertex.getProperty(DEAD_PATHS);
            final List<List<Object>> livePaths = new ArrayList<List<Object>>();

            for (final Vertex adjacent : this.incomingQuery.build(vertex).vertices()) {
                final List<List<Object>> paths = adjacent.getProperty(LIVE_PATHS);
                for (final List<Object> path : paths) {
                    final List<Object> appendedPath = new ArrayList<Object>();
                    appendedPath.addAll(path);
                    appendedPath.add(vertex.getId());
                    if (isSimple(appendedPath)) {
                        if (!deadPaths.containsKey(path.get(0))) {
                            deadPaths.put(path.get(0), appendedPath);
                        }
                        livePaths.add(appendedPath);
                    }
                }
            }
            vertex.setProperty(LIVE_PATHS, livePaths);
            vertex.setProperty(DEAD_PATHS, deadPaths);
        }
    }

    private boolean isSimple(final List list) {
        return new HashSet(list).size() == list.size();
    }

    public boolean terminate(final GraphMemory memory) {
        return memory.getIteration() == this.diameter;
    }

    public static Builder create() {
        return new Builder();
    }

    //////////////////////////////

    public static class Builder {

        private final ShortestPathProgram vertexProgram = new ShortestPathProgram();

        public Builder incoming(final VertexQueryBuilder incomingQuery) {
            this.vertexProgram.incomingQuery = incomingQuery;
            return this;
        }

        public Builder diameter(final int diameter) {
            this.vertexProgram.diameter = diameter;
            return this;
        }

        public ShortestPathProgram build() {
            return this.vertexProgram;
        }
    }

}
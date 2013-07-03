package com.tinkerpop.furnace.algorithms.vertexcentric.programs.paths;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ShortestPathProgram {} /*implements VertexProgram {

    private final VertexQueryBuilder incomingQuery;

    public ShortestPathProgram(final VertexQueryBuilder incomingQuery) {
        this.incomingQuery = incomingQuery;
    }

    public void setup(final Vertex vertex, final GraphMemory globalMemory) {
        final List<List<Object>> paths = new ArrayList<List<Object>>();
        final List<Object> path = new ArrayList<Object>();
        path.add(vertex.getId());
        paths.add(path);
        vertex.setProperty(ShortestPathGraphComputer.LIVE_PATHS, paths);
        vertex.setProperty(ShortestPathGraphComputer.DEAD_PATHS, new HashMap<Object, List<Vertex>>());
    }

    public void execute(final Vertex vertex, final GraphMemory globalMemory) {
        final Map<Object, List<Object>> deadPaths = vertex.getProperty(ShortestPathGraphComputer.DEAD_PATHS);
        final List<List<Object>> livePaths = new ArrayList<List<Object>>();

        for (final Vertex adjacent : this.incomingQuery.build(vertex).vertices()) {
            final List<List<Object>> paths = adjacent.getProperty(ShortestPathGraphComputer.LIVE_PATHS);
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
        vertex.setProperty(ShortestPathGraphComputer.LIVE_PATHS, livePaths);
        vertex.setProperty(ShortestPathGraphComputer.DEAD_PATHS, deadPaths);
    }

    private boolean isSimple(final List list) {
        return new HashSet(list).size() == list.size();
    }

}*/
package com.tinkerpop.furnace.algos;

import com.tinkerpop.blueprints.pgm.Element;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ShortestPath {

    private final Graph graph;

    public ShortestPath(final Graph graph) {
        this.graph = graph;
    }

    public List<Element> compute(final Vertex start, final Vertex end, final String... labels) {
        return new ArrayList<Element>();
    }
}

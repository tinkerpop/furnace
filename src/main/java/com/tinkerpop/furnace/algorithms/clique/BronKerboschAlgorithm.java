package com.tinkerpop.furnace.algorithms.clique;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * Implementation of the <a href="http://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm">Bron-Kerbosch Algorithm</a>. Since we're not working with a completely undirected graph, I've made this class work only on nodes that are bidirectionally connected.
 * 
 * @author Nicholas A. Stuart
 * 
 */
public class BronKerboschAlgorithm implements CliqueAlgorithm {
	private Graph graph;

	public BronKerboschAlgorithm(Graph graph) {
		this.graph = graph;
	}

	@Override
	public Set<Set<Vertex>> findAllMaximalCliques() {
		List<Vertex> vertexList = new LinkedList<Vertex>();

		for (Vertex vertex : graph.getVertices()) {
			vertexList.add(vertex);
		}

		Set<Set<Vertex>> results = new HashSet<Set<Vertex>>();
		recursiveWithPivot(new HashSet<Vertex>(), new HashSet<Vertex>(vertexList), new HashSet<Vertex>(), results);
		return results;
	}

	private void recursiveWithPivot(Set<Vertex> includeAll, Set<Vertex> includeSome, Set<Vertex> includeNone, Set<Set<Vertex>> completedList) {
		if (includeSome.isEmpty() && includeNone.isEmpty()) {
			if (includeAll.size() > 1) {
				completedList.add(includeAll);
			}
			return;
		}
		Vertex pivot = null;
		if (!includeSome.isEmpty()) {
			pivot = includeSome.iterator().next();
		} else {
			pivot = includeNone.iterator().next();
		}

		Set<Vertex> bidirectionalNeighbors = getAllBidirectionalNeighbors(pivot);

		Set<Vertex> iteratingSet = new HashSet<Vertex>(includeSome);
		iteratingSet.removeAll(bidirectionalNeighbors);

		for (Vertex v : iteratingSet) {
			Set<Vertex> tempIncludeAll = new HashSet<Vertex>(includeAll);
			Set<Vertex> tempIncludeSome = new HashSet<Vertex>(includeSome);
			Set<Vertex> tempIncludeNone = new HashSet<Vertex>(includeNone);
			Set<Vertex> tempBirectionalNeighbors = getAllBidirectionalNeighbors(v);

			tempIncludeAll.add(v);
			tempIncludeSome.retainAll(tempBirectionalNeighbors);
			tempIncludeNone.retainAll(tempBirectionalNeighbors);

			recursiveWithPivot(tempIncludeAll, tempIncludeSome, tempIncludeNone, completedList);
			includeSome.remove(v);
			includeNone.add(v);
		}

	}

	private Set<Vertex> getAllBidirectionalNeighbors(Vertex vertex) {
		Set<Vertex> outgoingNeighbors = new HashSet<Vertex>();
		for (Edge fence : vertex.getEdges(Direction.OUT)) {
			outgoingNeighbors.add(fence.getVertex(Direction.IN));
		}

		Set<Vertex> bidirectionalNeighbors = new HashSet<Vertex>();
		for (Edge fence : vertex.getEdges(Direction.IN)) {
			Vertex neighbor = fence.getVertex(Direction.OUT);
			if (outgoingNeighbors.contains(neighbor)) {
				bidirectionalNeighbors.add(neighbor);
			}
		}
		return bidirectionalNeighbors;
	}
}
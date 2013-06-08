package com.tinkerpop.furnace.algorithms.graphcentric.searching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class BreadthFirstAlgorithm implements SearchAlgorithm, TraversalAlgorithm {
	private Graph graph;

	public BreadthFirstAlgorithm(Graph graph) {
		this.graph = graph;
	}

	@Override
	public List<Vertex> traverseTree(Vertex start) {
		if (start == null) {
			throw new NullPointerException("Start cannot be null");
		}

		start = graph.getVertex(start.getId());
		if (start == null) {
			throw new IllegalStateException("Start vertex does not belong to this graph.");
		}

		return performBreadthFirstTraversal(start);
	}

	private List<Vertex> performBreadthFirstTraversal(Vertex start) {
		Queue<Vertex> list = new LinkedList<Vertex>();
		Set<Vertex> visitedSet = new HashSet<Vertex>();
		LinkedList<Vertex> returnList = new LinkedList<Vertex>();

		list.add(start);
		visitedSet.add(start);
		while (!list.isEmpty()) {
			Vertex next = list.poll();
			returnList.add(next);
			for (Edge edge : next.getEdges(Direction.OUT)) {
				Vertex child = edge.getVertex(Direction.IN);
				if (!visitedSet.contains(child)) {
					visitedSet.add(child);
					list.add(child);
				}
			}
		}

		return new ArrayList<Vertex>(returnList);
	}

	@Override
	public List<Edge> findPathToTarget(Vertex start, Vertex target) {
		if (start == null) {
			throw new NullPointerException("Start cannot be null");
		}
		if (target == null) {
			throw new NullPointerException("Target cannot be null");
		}

		start = graph.getVertex(start.getId());
		if (start == null) {
			throw new IllegalStateException("Start vertex does not belong to this graph.");
		}
		target = graph.getVertex(target.getId());
		if (target == null) {
			throw new IllegalStateException("Target vertex does not belong to this graph.");
		}

		if (start.equals(target)) {
			return new LinkedList<Edge>();
		}

		return performBreadthFirstSearch(start, target);
	}

	private List<Edge> performBreadthFirstSearch(Vertex start, Vertex end) {
		Queue<Vertex> list = new LinkedList<Vertex>();
		Set<Vertex> visitedSet = new HashSet<Vertex>();
		Map<Vertex, Edge> previousMap = new HashMap<Vertex, Edge>();

		list.add(start);
		visitedSet.add(start);
		while (!list.isEmpty()) {
			Vertex next = list.poll();
			if (end.equals(next)) {
				break;
			}
			for (Edge edge : next.getEdges(Direction.OUT)) {
				Vertex child = edge.getVertex(Direction.IN);
				if (!visitedSet.contains(child)) {
					previousMap.put(child, edge);
					visitedSet.add(child);
					list.add(child);
				}
			}
		}

		List<Edge> pathFromStartToEnd = new LinkedList<Edge>();
		Vertex previousVertex = end;
		while (!start.equals(previousVertex)) {
			Edge currentEdge = previousMap.get(previousVertex);
			pathFromStartToEnd.add(currentEdge);
			previousVertex = currentEdge.getVertex(Direction.OUT);
		}
		Collections.reverse(pathFromStartToEnd);
		return pathFromStartToEnd;
	}
}
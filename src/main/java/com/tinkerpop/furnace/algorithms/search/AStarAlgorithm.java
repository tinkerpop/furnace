package com.tinkerpop.furnace.algorithms.search;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.util.HeuristicAlgorithm;

public class AStarAlgorithm implements SearchAlgorithm {
	private Graph graph;
	private HeuristicAlgorithm heuristicAlgorithm;

	public AStarAlgorithm(Graph graph, HeuristicAlgorithm heuristicAlgorithm) {
		this.graph = graph;
		this.heuristicAlgorithm = heuristicAlgorithm;
	}

	@Override
	public List<Edge> findPathToTarget(Vertex start, Vertex goal) {
		if (start == null) {
			throw new NullPointerException("Start cannot be null");
		}
		if (goal == null) {
			throw new NullPointerException("Target cannot be null");
		}

		start = graph.getVertex(start.getId());
		if (start == null) {
			throw new IllegalStateException("Start vertex does not belong to this graph.");
		}
		goal = graph.getVertex(goal.getId());
		if (goal == null) {
			throw new IllegalStateException("Target vertex does not belong to this graph.");
		}

		if (start.equals(goal)) {
			return new LinkedList<Edge>();
		}

		return performAStartSearch(start, goal);
	}

	private List<Edge> performAStartSearch(Vertex start, Vertex goal) {
		Set<Vertex> closedSet = new HashSet<Vertex>();
		Set<Vertex> openSet = new HashSet<Vertex>(Collections.singleton(start));
		Map<Vertex, Edge> cameFrom = new HashMap<Vertex, Edge>();

		Map<Vertex, Long> gScores = new HashMap<Vertex, Long>();
		Map<Vertex, Long> fScores = new HashMap<Vertex, Long>();

		gScores.put(start, 0L);
		fScores.put(start, heuristicCostEstimate(start, goal));

		while (!openSet.isEmpty()) {
			Vertex current = smalledFScoreFromOpenSet(openSet, fScores);

			if (goal.equals(current)) {
				return reconstructPath(start, goal, cameFrom);
			}

			openSet.remove(current);
			closedSet.add(current);
			for (Edge edge : current.getEdges(Direction.OUT)) {
				Vertex neighbor = edge.getVertex(Direction.IN);
				long tenativegScore = gScores.get(current) + (Long) edge.getProperty("weight");
				if (closedSet.contains(neighbor)) {
					if (tenativegScore >= gScores.get(neighbor)) {
						continue;
					}
				}

				if ((!openSet.contains(neighbor)) || tenativegScore < gScores.get(neighbor)) {
					cameFrom.put(neighbor, edge);
					gScores.put(neighbor, tenativegScore);
					fScores.put(neighbor, gScores.get(neighbor) + heuristicCostEstimate(neighbor, goal));
					if (!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}
			}
		}

		return new LinkedList<Edge>();
	}

	private Vertex smalledFScoreFromOpenSet(Set<Vertex> openSet, Map<Vertex, Long> fScores) {
		Long minimum = null;
		Vertex minimumVertex = null;
		for (Vertex vertex : openSet) {
			Long fScore = fScores.get(vertex);
			if (minimum == null) {
				minimum = fScore;
				minimumVertex = vertex;
			} else if (fScore < minimum) {
				minimum = fScore;
				minimumVertex = vertex;
			}
		}
		return minimumVertex;
	}

	private List<Edge> reconstructPath(Vertex start, Vertex goal, Map<Vertex, Edge> cameFrom) {
		List<Edge> returnableList = new LinkedList<Edge>();

		if (!start.equals(goal)) {
			Vertex pastVertex = null;
			Edge currentEdge = cameFrom.get(goal);
			do {
				pastVertex = currentEdge.getVertex(Direction.OUT);
				returnableList.add(currentEdge);
				currentEdge = cameFrom.get(pastVertex);
			} while (!start.equals(pastVertex));

			Collections.reverse(returnableList);
		}

		return returnableList;
	}

	private Long heuristicCostEstimate(Vertex start, Vertex goal) {
		if (heuristicAlgorithm == null) {
			return 0L;
		} else {
			return heuristicAlgorithm.costEstimation(graph, start, goal);
		}
	}
}

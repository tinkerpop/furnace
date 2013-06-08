package com.tinkerpop.furnace.algorithms.graphcentric.paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * 
 * Implementation of Bellman-Ford's single source shortest path algorithm, which will work with negative edge weights, provided there are no negative weight cycles.
 * 
 * @author Nicholas A. Stuart
 * 
 */
public class BellmanFordAlgorithm extends SingleSourceShortestPathAlgorithm {

	public BellmanFordAlgorithm(Graph graph) {
		super(graph);
	}

	@Override
	public Map<Vertex, List<Edge>> compute(Vertex source, String weightPropertyName, String... weightedEdgeLabels) {
		if (source == null) {
			throw new NullPointerException("Source cannot be null");
		}
		if (weightedEdgeLabels == null) {
			throw new NullPointerException("Weighted edge labels cannot be null");
		}
		if (weightPropertyName == null || weightPropertyName.isEmpty()) {
			throw new IllegalArgumentException("Weight property name cannot be null or empty");
		}
		for (String weightedEdgeLabel : weightedEdgeLabels) {
			if (weightedEdgeLabel == null) {
				throw new IllegalArgumentException("One of the weighted edge labels was null");
			}
		}

		source = graph.getVertex(source.getId());
		if (source == null) {
			throw new IllegalStateException("Source vertex does not belong to this graph.");
		}

		return performBellmanFordsAlgorithm(source, weightPropertyName, new HashSet<String>(Arrays.asList(weightedEdgeLabels)));
	}

	private Map<Vertex, List<Edge>> performBellmanFordsAlgorithm(Vertex source, String weightPropertyName, Set<String> weightedEdgeLabels) {
		Map<Vertex, Double> distanceMap = new HashMap<Vertex, Double>();
		Map<Vertex, Edge> predecessorMap = new HashMap<Vertex, Edge>();

		long verticesSize = 0;
		for (Vertex v : graph.getVertices()) {
			distanceMap.put(v, Double.POSITIVE_INFINITY);
			verticesSize++;
		}

		distanceMap.put(source, 0D);

		Set<Edge> edgesToCheck = new HashSet<Edge>();
		for (Edge edge : graph.getEdges()) {
			if (!weightedEdgeLabels.isEmpty()) {
				if (!weightedEdgeLabels.contains(edge.getLabel())) {
					continue;
				}
			}
			edgesToCheck.add(edge);
		}

		for (int i = 0; i < verticesSize - 1; i++) {
			for (Edge edge : edgesToCheck) {
				long edgeWeight = (Long)edge.getProperty(weightPropertyName);
				Vertex from = edge.getVertex(Direction.OUT);
				Vertex to = edge.getVertex(Direction.IN);

				Double fromPlusWeightDistance = (distanceMap.get(from) + edgeWeight);
				Double toDistance = distanceMap.get(to);
				if (fromPlusWeightDistance < toDistance) {
					distanceMap.put(to, fromPlusWeightDistance);
					predecessorMap.put(to, edge);
				}
			}
		}

		for (Edge edge : edgesToCheck) {
			long edgeWeight = (Long)edge.getProperty(weightPropertyName);
			Vertex from = edge.getVertex(Direction.OUT);
			Vertex to = edge.getVertex(Direction.IN);

			Double fromPlusWeightDistance = (distanceMap.get(from) + edgeWeight);
			Double toDistance = distanceMap.get(to);
			if (fromPlusWeightDistance < toDistance) {
				throw new IllegalStateException("Graph containes a negative weight cycle");
			}
		}

		Map<Vertex, List<Edge>> pathMap = new HashMap<Vertex, List<Edge>>();
		for (Entry<Vertex, Edge> entry : predecessorMap.entrySet()) {
			List<Edge> path = new ArrayList<Edge>();
			pathMap.put(entry.getKey(), path);

			Vertex pastVertex = null;
			Edge currentEdge = entry.getValue();
			do {
				pastVertex = currentEdge.getVertex(Direction.OUT);
				path.add(currentEdge);
				currentEdge = predecessorMap.get(pastVertex);
			} while (!source.equals(pastVertex));

			Collections.reverse(path);
		}
		return pathMap;
	}
}
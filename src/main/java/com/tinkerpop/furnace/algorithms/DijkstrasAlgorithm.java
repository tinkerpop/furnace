package com.tinkerpop.furnace.algorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * 
 * Implementation of Dijkstra's Algorithm using Tinkerpop's Pipelines.
 * 
 * @author Nicholas A. Stuart (nicholasastuart@gmail.com)
 * 
 */
public class DijkstrasAlgorithm extends SingleSourceShortestPathAlgorithm {
	public DijkstrasAlgorithm(Graph graph) {
		super(graph);
	}

	@Override
	public Map<Vertex, Long> compute(Vertex source, String weightPropertyName, String... weightedEdgeLabels) {
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

		Graph graph = getGraph();
		source = graph.getVertex(source.getId());
		if (source == null) {
			throw new IllegalStateException("Source vertex does not belong to this graph.");
		}
		return performDijkstrasAlgorithm(graph, source, weightPropertyName, weightedEdgeLabels);
	}

	private Map<Vertex, Long> performDijkstrasAlgorithm(Graph graph, Vertex source, String weightPropertyName, final String... weightedEdgeLabels) {
		final Map<Vertex, Long> distanceMap = new HashMap<Vertex, Long>();
		Set<Vertex> remainingNodes = new HashSet<Vertex>();
		// Map<Vertex, Edge> previousMap = new HashMap<Vertex, Edge>();

		Comparator<Vertex> distanceComparator = new Comparator<Vertex>() {
			@Override
			public int compare(Vertex vertex1, Vertex vertex2) {
				return (int) (distanceMap.get(vertex2) - distanceMap.get(vertex1));
			}
		};

		for (Vertex vertex : graph.getVertices()) {
			distanceMap.put(vertex, Long.MAX_VALUE);
			remainingNodes.add(vertex);
		}
		distanceMap.put(source, 0L);

		while (!remainingNodes.isEmpty()) {
			Vertex smallestVertex = Collections.min(remainingNodes, distanceComparator);
			remainingNodes.remove(smallestVertex);
			Long currentDistance = distanceMap.get(smallestVertex);
			if (currentDistance == Long.MAX_VALUE) {
				break;
			}

			for (Edge edge : smallestVertex.getEdges(Direction.OUT, weightedEdgeLabels)) {
				Vertex neighborVertex = edge.getVertex(Direction.IN);
				if (remainingNodes.contains(neighborVertex)) {
					Long edgeWeight = null;
					try {
						edgeWeight = Long.valueOf(edge.getProperty(weightPropertyName).toString());
					} catch (NumberFormatException nfe) {
						throw new IllegalArgumentException("Edge " + edge + " weight property not castable to Long");
					} catch (NullPointerException npe) {
						throw new IllegalArgumentException("Edge " + edge + " did not contain the property key " + weightPropertyName);
					}
					Long couldBeShorterDistance = currentDistance + edgeWeight;
					if (couldBeShorterDistance < currentDistance) {
						couldBeShorterDistance = Long.MAX_VALUE;
					}
					if (couldBeShorterDistance < distanceMap.get(neighborVertex)) {
						distanceMap.put(neighborVertex, couldBeShorterDistance);
						// previousMap.put(neighborVertex, edge);
					}
				}
			}
		}
		return distanceMap;
	}
}
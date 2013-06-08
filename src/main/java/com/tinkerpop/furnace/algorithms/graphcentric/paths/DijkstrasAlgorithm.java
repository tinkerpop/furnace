package com.tinkerpop.furnace.algorithms.graphcentric.paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * 
 * Implementation of Dijkstra's Algorithm, which will not work with negative edge weights.
 * 
 * @author Nicholas A. Stuart (nicholasastuart@gmail.com)
 * 
 */
public class DijkstrasAlgorithm extends SingleSourceShortestPathAlgorithm {
	public DijkstrasAlgorithm(Graph graph) {
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
		return performDijkstrasAlgorithm(source, weightPropertyName, weightedEdgeLabels);
	}

	private Map<Vertex, List<Edge>> performDijkstrasAlgorithm(Vertex source, String weightPropertyName, final String... weightedEdgeLabels) {
		final Map<Vertex, Long> distanceMap = new HashMap<Vertex, Long>();
		Set<Vertex> remainingNodes = new HashSet<Vertex>();
		Map<Vertex, Edge> predecessorMap = new HashMap<Vertex, Edge>();

		// Compares the distances of the vertexes from the map
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
			// Since we constantly need to get the min, which will change every iteration, we're unable to utilize a heap.
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
						if (edgeWeight < 0) {
							throw new IllegalArgumentException("Weight on Edge " + edge + " was negative, and this implementation of Dijkstra's does not allow negative edge weights");
						}
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
						predecessorMap.put(neighborVertex, edge);
					}
				}
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
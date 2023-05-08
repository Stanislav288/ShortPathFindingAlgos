package com.aldekain.short_path_algos;

import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.TreeSingleSourcePathsImpl;
import org.jgrapht.alg.util.Pair;

import java.util.*;

/**
 * This class is a similar to the {@link BFSShortestPath} class
 * with the difference with that it uses {@link Stack} instead {@link Deque}
 * which is the main difference between BFS and DFS
 *
 */
public class DFSShortestPath<V, E> extends org.jgrapht.alg.shortestpath.BFSShortestPath<V, E> {

	/**
	 * Construct a new instance.
	 *
	 * @param graph
	 * 			the input graph
	 */
	public DFSShortestPath(org.jgrapht.Graph graph) {
		super(graph);
	}

	@Override
	public SingleSourcePaths<V, E> getPaths(V source){
		if (!graph.containsVertex(source)) {
			throw new IllegalArgumentException(GRAPH_MUST_CONTAIN_THE_SOURCE_VERTEX);
		}

		/*
		 * Initialize distanceAndPredecessorMap
		 */
		Map<V, Pair<Double, E>> distanceAndPredecessorMap = new HashMap<>();
		distanceAndPredecessorMap.put(source, Pair.of(0d, null));

		/*
		 * Declaring stack
		 */
		Stack<V> stack = new Stack<>();
		stack.push(source);

		/*
		 * Take the top most vertex from the stack, relax its outgoing edges, update the distance of
		 * the neighbouring vertices and push them into the stack
		 */
		while (!stack.isEmpty()) {
			V v = stack.pop();
			for (E e : graph.outgoingEdgesOf(v)) {
				V u = Graphs.getOppositeVertex(graph, e, v);
				if (!distanceAndPredecessorMap.containsKey(u)) {
					stack.add(u);
					double newDist = distanceAndPredecessorMap.get(v).getFirst() + 1.0;
					distanceAndPredecessorMap.put(u, Pair.of(newDist, e));
				}
			}
		}

		return new TreeSingleSourcePathsImpl<>(graph, source, distanceAndPredecessorMap);
	}
}

package com.aldekain.short_path_algos;

import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.util.SupplierUtil;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

	public static void main(String[] args) throws URISyntaxException, IOException {
		org.jgrapht.Graph<CoordinateVertex, ExtentedEdge> jgraphtGraph = new DefaultUndirectedWeightedGraph<>(
				SupplierUtil.createSupplier(CoordinateVertex.class), SupplierUtil.createSupplier(ExtentedEdge.class));

		int numberOfNodes = 20;
		double p = 0.3d;
		boolean displayGraph = true;

		GenerateRandomGraph generateRandomGraph = new GenerateRandomGraph(jgraphtGraph, numberOfNodes, p);
		generateRandomGraph.generateGraph();

		CoordinateVertex startVertex = jgraphtGraph.vertexSet().stream().filter(v -> v.getId() == 0).findFirst().get();
		int vertexSetLastElementId = jgraphtGraph.vertexSet().size() / 2 - 1;
		CoordinateVertex endVertex = jgraphtGraph.vertexSet().stream().filter(v ->v.getId() == vertexSetLastElementId).findFirst().get();

		ShortPathAnalyzer bfs = new ShortPathAnalyzer(
				jgraphtGraph,
				new BFSShortestPath<>(jgraphtGraph),
				startVertex,
				endVertex,
				displayGraph);
		bfs.analyze();

		ShortPathAnalyzer dfs = new ShortPathAnalyzer(
				jgraphtGraph,
				new DFSShortestPath<>(jgraphtGraph),
				startVertex,
				endVertex,
				displayGraph);
		dfs.analyze();

		ShortPathAnalyzer dijkstra = new ShortPathAnalyzer(
				jgraphtGraph,
				new DijkstraShortestPath<>(jgraphtGraph),
				startVertex,
				endVertex,
				displayGraph);
		dijkstra.analyze();

		AStarBasicHeuristic heuristic = new AStarBasicHeuristic();
		ShortPathAnalyzer aStar = new ShortPathAnalyzer(
				jgraphtGraph,
				new AStarShortestPath<>(jgraphtGraph, heuristic),
				startVertex,
				endVertex,
				displayGraph);
		aStar.analyze();

		ShortPathAnalyzer bellmanFordShortestPath = new ShortPathAnalyzer(
				jgraphtGraph,
				new BellmanFordShortestPath<>(jgraphtGraph),
				startVertex,
				endVertex,
				displayGraph);
		bellmanFordShortestPath.analyze();

		ShortPathAnalyzer floydWarshall = new ShortPathAnalyzer(
				jgraphtGraph,
				new FloydWarshallShortestPaths<>(jgraphtGraph),
				startVertex,
				endVertex,
				displayGraph);
		floydWarshall.analyze();
	}
}

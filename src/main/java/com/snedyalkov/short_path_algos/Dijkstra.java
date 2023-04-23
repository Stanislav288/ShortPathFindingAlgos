package com.snedyalkov.short_path_algos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.math3.ml.distance.ManhattanDistance;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.generate.GnmRandomGraphGenerator;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.util.SupplierUtil;

public class Dijkstra {

	public static void main(String[] args) throws IOException, URISyntaxException {
		org.jgrapht.Graph<Integer, ExtentedEdge> jgraphtGraph = new DefaultUndirectedWeightedGraph<>(
				SupplierUtil.createIntegerSupplier(0), SupplierUtil.createSupplier(ExtentedEdge.class));

		int numberOfNodes = 10000;
		double p = 0.1;
		int numberOfVertexes = (int) (p * numberOfNodes * (numberOfNodes - 1) / 2);

		//StopWatch class is used to measure elapsed time.
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		GnmRandomGraphGenerator<Integer, ExtentedEdge> gnmGenerator =
				new GnmRandomGraphGenerator<>(numberOfNodes, numberOfVertexes);
		gnmGenerator.generateGraph(jgraphtGraph);
//		System.out.println("graph.vertexSet() = " + jgraphtGraph.vertexSet());
//
//		System.out.println("graph.edgeSet() = " + jgraphtGraph.edgeSet());
		SecureRandom secureRandom = new SecureRandom();

		for (ExtentedEdge edge : jgraphtGraph.edgeSet()) {
			double weight = secureRandom.nextDouble(1, 101);
			jgraphtGraph.setEdgeWeight(edge, weight);
//			System.out.println("edge => " + edge + "; weight=" + weight);
//			System.out.println("edge => " + edge + "; edge.getWeight()=" + edge.getWeight());

		}

		System.out.println("stopWatch Graph Generation => " + stopWatch);

		//StopWatch class is used to measure elapsed time.
		stopWatch.reset();
		stopWatch.start();

		ShortestPathAlgorithm<Integer, ExtentedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(
				jgraphtGraph);

		org.jgrapht.GraphPath<Integer, ExtentedEdge> graphPath =
				dijkstraShortestPath.getPath(1, jgraphtGraph.vertexSet().size() - 1);
		List<Integer> shortestPathVertexes = graphPath.getVertexList();
		List<ExtentedEdge> shortestPathEdges = graphPath.getEdgeList();

		System.out.println("shortestPath Dijkstra = " + shortestPathVertexes);

        stopWatch.stop();
        //Prints the time needed for the execution of the code placed between
        //stopWatch.start() and stopWatch.stop()
        System.out.println("stopWatch Dijkstra => " + stopWatch);

		////////////////////////////////////////////////////////////////////////

		stopWatch.reset();
		stopWatch.start();
		ShortestPathAlgorithm<Integer, ExtentedEdge> bfsShortestPath = new BFSShortestPath<>(
				jgraphtGraph);

		org.jgrapht.GraphPath<Integer, ExtentedEdge> graphPathBfs =
				bfsShortestPath.getPath(1, jgraphtGraph.vertexSet().size() - 1);
		List<Integer> bfsShortestPathVertexes = graphPathBfs.getVertexList();
		List<ExtentedEdge> bfsShortestPathEdges = graphPathBfs.getEdgeList();

		stopWatch.stop();
		System.out.println("stopWatch DFS = " + stopWatch);
		System.out.println("shortestPath DFS = " + bfsShortestPathVertexes);



		////////////////////////////////////////////////////////////////////////
		stopWatch.reset();
		stopWatch.start();
		ShortestPathAlgorithm<Integer, ExtentedEdge> dfsShortestPath = new DFSShortestPath<>(
				jgraphtGraph);
		org.jgrapht.GraphPath<Integer, ExtentedEdge> graphPathDfs =
				dfsShortestPath.getPath(1, jgraphtGraph.vertexSet().size() - 1);
		List<Integer> dfsShortestPathVertexes = graphPathDfs.getVertexList();
		List<ExtentedEdge> dfsShortestPathEdges = graphPathDfs.getEdgeList();

		stopWatch.stop();
		System.out.println("stopWatch DFS = " + stopWatch);
		System.out.println("shortestPath DFS = " + dfsShortestPathVertexes);


		////////////////////////////////////////////////////////////////////////

		AStarAdmissibleHeuristic<String> heuristic = new MyHeuristic<>(graph, "C");

		AStarAdmissibleHeuristic<Node> heuristic = new ManhattanDistance();
		this.readLabyrinth(labyrinth1);

		stopWatch.reset();
		stopWatch.start();
		ShortestPathAlgorithm<Integer, ExtentedEdge> aStarShortestPath = new AStarShortestPath<>(
				jgraphtGraph, new ALTAdmissibleHeuristic());
		org.jgrapht.GraphPath<Integer, ExtentedEdge> graphPathDfs =
				dfsShortestPath.getPath(1, jgraphtGraph.vertexSet().size() - 1);
		List<Integer> dfsShortestPathVertexes = graphPathDfs.getVertexList();
		List<ExtentedEdge> dfsShortestPathEdges = graphPathDfs.getEdgeList();

		stopWatch.stop();
		System.out.println("stopWatch DFS = " + stopWatch);
		System.out.println("shortestPath DFS = " + dfsShortestPathVertexes);


		////////////////////////////////////////////////////////////////////////

		Graph graphstreamGraph = GraphMapper.convertGraph(jgraphtGraph);

		for (Node node : graphstreamGraph) {
			node.setAttribute("ui.label", node.getId());
		}

		for (Integer node : shortestPathVertexes) {
			graphstreamGraph.getNode(node).setAttribute("ui.style", "fill-color: blue;");
		}

		for (ExtentedEdge edge : jgraphtGraph.edgeSet()) {
			if(shortestPathEdges.contains(edge)) {
				graphstreamGraph.getEdge(edge.toString()).setAttribute("ui.style", "fill-color: red;");

			}

			graphstreamGraph.getEdge(edge.toString()).setAttribute("ui.label", String.format("%.2f", edge.getWeight()));
		}

		//displayGraph(graphstreamGraph);
	}

	private static void displayGraph(Graph graph) throws IOException, URISyntaxException {
		System.setProperty("org.graphstream.ui", "javafx");
		System.setProperty("org.graphstream.debug", "true");

		String stylesheet = Files.readString(Paths.get(Dijkstra.class.getResource("/default.css").toURI()));
		graph.setAttribute("ui.stylesheet", stylesheet);

		graph.display();
	}

	public static class ManhattanDistance
			implements
			AStarAdmissibleHeuristic<Node>
	{
		@Override
		public double getCostEstimate(Node sourceVertex, Node targetVertex)
		{sourceVertex.
			return Math.abs(sourceVertex.x - targetVertex.x)
					+ Math.abs(sourceVertex.y - targetVertex.y);
		}
	}

	public static class EuclideanDistance
			implements
			AStarAdmissibleHeuristic<Node>
	{
		@Override
		public double getCostEstimate(Node sourceVertex, Node targetVertex)
		{
			return Math
					.sqrt(
							Math.pow(sourceVertex.x - targetVertex.x, 2)
									+ Math.pow(sourceVertex.y - targetVertex.y, 2));
		}
	}
}

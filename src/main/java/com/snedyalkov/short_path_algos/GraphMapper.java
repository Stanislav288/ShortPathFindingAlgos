package com.snedyalkov.short_path_algos;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.jgrapht.alg.util.Triple;

public class GraphMapper {

	private static AtomicInteger counter = new AtomicInteger();

	private GraphMapper() {
	}

	public static <V, E> Graph convertGraph(org.jgrapht.Graph<V, E> jgrapht) {
        Graph graphstream = new SingleGraph("Graphstream Graph " + counter.getAndIncrement());

        for (V vertex : jgrapht.vertexSet()) {
            graphstream.addNode(vertex.toString());
        }

        for (E currentEdge : jgrapht.edgeSet()) {
	        Triple<String, String, String> triple = extractEdgeVertexes(currentEdge.toString());

        	int source = Integer.parseInt(triple.getFirst());
        	int target = Integer.parseInt(triple.getSecond());
        	double weight = Double.parseDouble(triple.getThird());

        	graphstream.addEdge(currentEdge.toString(), source, target).setAttribute("length", weight);
        }


      return graphstream;
	}


	private static Triple<String, String, String> extractEdgeVertexes(String input){
		Pattern pattern = Pattern.compile("\\((.*)\\s:\\s(.*)\\s:\\s(.*)\\)");
        Matcher matcher = pattern.matcher(input);

        String source = null;
        String target = null;
        String weight = null;

        if (matcher.find()) {
        	source = matcher.group(1);
        	target = matcher.group(2);
        	weight = matcher.group(3);
        }

		return new Triple<>(source, target, weight);
	}
}

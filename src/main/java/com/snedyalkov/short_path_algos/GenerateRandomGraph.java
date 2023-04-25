package com.snedyalkov.short_path_algos;

import org.apache.commons.lang3.time.StopWatch;
import org.jgrapht.Graph;
import org.jgrapht.generate.GnpRandomGraphGenerator;

import java.security.SecureRandom;

public class GenerateRandomGraph {

    private Graph<CoordinateVertex, ExtentedEdge> jgraphtGraph;

    private int numberOfNodes;

    private double p;

    public GenerateRandomGraph(Graph jgraphtGraph,
                               int numberOfNodes,
                               double p){
        this.jgraphtGraph = jgraphtGraph;
        this.numberOfNodes = numberOfNodes;
        this.p = p;
    }

    public void generateGraph(){
        //StopWatch class is used to measure elapsed time.
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        GnpRandomGraphGenerator<CoordinateVertex, ExtentedEdge> graphGenerator =
                new GnpRandomGraphGenerator<>(numberOfNodes, p);
        graphGenerator.generateGraph(jgraphtGraph);

        SecureRandom secureRandom = new SecureRandom();

        for (ExtentedEdge edge : jgraphtGraph.edgeSet()) {
            double weight = secureRandom.nextDouble(1, 101);
            jgraphtGraph.setEdgeWeight(edge, weight);

        }

        System.out.println("StopWatch Graph Generation => " + stopWatch);
        System.out.println("====================================================================================");
        //StopWatch class is used to measure elapsed time.
        stopWatch.reset();
    }
}

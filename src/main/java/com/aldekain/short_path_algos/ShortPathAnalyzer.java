package com.aldekain.short_path_algos;

import org.apache.commons.lang3.time.StopWatch;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ShortPathAnalyzer {

    static {
        //System.setProperty("org.graphstream.ui", "javafx");
        System.setProperty("org.graphstream.ui", "swing");
        System.setProperty("org.graphstream.debug", "true");
    }
    private final StopWatch stopWatch;

    private final org.jgrapht.Graph<CoordinateVertex, ExtentedEdge> jgraphtGraph;

    private final ShortestPathAlgorithm<CoordinateVertex, ExtentedEdge> shortestPathAlgorithm;

    private final CoordinateVertex startVertex;
    private final CoordinateVertex endVertex;

    private final boolean displayGraph;

    private List<CoordinateVertex> shortestPathVertexes;

    private List<ExtentedEdge> shortestPathEdges;

    private String algorithmName;

    public ShortPathAnalyzer(org.jgrapht.Graph<CoordinateVertex, ExtentedEdge> jgraphtGraph,
            ShortestPathAlgorithm<CoordinateVertex, ExtentedEdge> shortestPathAlgorithm,
                                CoordinateVertex startVertex,
                                CoordinateVertex endVertex,
                                boolean displayGraph){
        this.stopWatch = new StopWatch();
        this.jgraphtGraph = jgraphtGraph;
        this.shortestPathAlgorithm = shortestPathAlgorithm;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.displayGraph = displayGraph;
        this.algorithmName = shortestPathAlgorithm.getClass().getSimpleName();
    }

    private void preAnalyze(){
        stopWatch.start();
    }

    public void analyze() throws URISyntaxException, IOException {
        this.preAnalyze();

        org.jgrapht.GraphPath<CoordinateVertex, ExtentedEdge> graphPath =
                        this.shortestPathAlgorithm.getPath(startVertex, endVertex);
        this.shortestPathVertexes = graphPath.getVertexList();
        this.shortestPathEdges = graphPath.getEdgeList();

        this.postAnalyze();

        if(this.displayGraph){
            this.visualizeGraph();
        }
    }

    private void postAnalyze(){
        stopWatch.stop();

        System.out.println("stopWatch " + algorithmName + " = " + stopWatch);
        System.out.println("shortestPathVertexes " + algorithmName + " = " + shortestPathVertexes);
        System.out.println("shortestPathEdges " + algorithmName + " = " + shortestPathEdges);
        System.out.println("====================================================================================");

        stopWatch.reset();
    }

    private void visualizeGraph() throws URISyntaxException, IOException {

        org.graphstream.graph.Graph graphstreamGraph = GraphMapper.convertGraph(jgraphtGraph);

        for (Node node : graphstreamGraph) {
            node.setAttribute("ui.label", node.getId());
        }

        for (CoordinateVertex node : shortestPathVertexes) {
            graphstreamGraph.getNode(node.getId()).setAttribute("ui.style", "fill-color: blue;");
        }

        for (ExtentedEdge edge : jgraphtGraph.edgeSet()) {
            if(shortestPathEdges.contains(edge)) {
                graphstreamGraph.getEdge(edge.toString()).setAttribute("ui.style", "fill-color: red;");
            }

            graphstreamGraph.getEdge(edge.toString()).setAttribute("ui.label", String.format("%.2f", edge.getWeight()));
        }

        String stylesheet = Files.readString(Paths.get(ShortPathAnalyzer.class.getResource("/default.css").toURI()));
        graphstreamGraph.setAttribute("ui.stylesheet", stylesheet);

        graphstreamGraph.setAttribute("ui.title", algorithmName);

        Viewer viewer = graphstreamGraph.display();
    }
}

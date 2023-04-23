package com.snedyalkov.short_path_algos;

import org.jgrapht.generate.GnmRandomGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        GnmRandomGraphGenerator<Integer, DefaultEdge> generator = new GnmRandomGraphGenerator<>(20,10);
        SimpleGraph<Integer, DefaultEdge> G = new SimpleGraph<>(DefaultEdge.class);

//        IntStream.range(0, 10).parallel().forEach(i -> {
//            DefaultEdge e = generator.getEdgeSupplier().get();
//            while (!G.addEdge(generator.getVertexSupplier().get(), generator.getVertexSupplier().get(), e)) {
//            }
//        });

        executor.shutdown();
    }
}

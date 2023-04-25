package com.snedyalkov.short_path_algos;

import com.snedyalkov.short_path_algos.CoordinateVertex;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;

public class AStarBasicHeuristic<V> implements AStarAdmissibleHeuristic<V> {

    private	EuclideanDistance euclideanDistance = new EuclideanDistance();

    @Override
    public double getCostEstimate(V sourceVertex, V targetVertex) {
        if(sourceVertex instanceof CoordinateVertex && targetVertex instanceof CoordinateVertex){
            double[] a = new double[]{((CoordinateVertex) sourceVertex).getX(), ((CoordinateVertex) sourceVertex).getY()};
            double[] b = new double[]{((CoordinateVertex) targetVertex).getX(), ((CoordinateVertex) targetVertex).getY()};

            return euclideanDistance.compute(a, b);
        }

        return 0;
    }

    @Override
    public boolean isConsistent(Graph graph) {
        return true;
    }
}

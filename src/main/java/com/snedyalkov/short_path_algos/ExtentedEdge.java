package com.snedyalkov.short_path_algos;

import org.jgrapht.graph.DefaultWeightedEdge;

public class ExtentedEdge extends DefaultWeightedEdge{

	/**
	 *
	 */
	private static final long serialVersionUID = -6319623114716716868L;

	@Override
	public double getWeight() {
		return super.getWeight();
	}

	@Override
	public String toString() {
		return new StringBuilder()
		.append("(").append(this.getSource())
		.append(" : ")
		.append(this.getTarget())
		.append(" : ")
		.append(this.getWeight()).append(")").toString();
	}
}

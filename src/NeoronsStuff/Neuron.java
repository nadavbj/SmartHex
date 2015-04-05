package NeoronsStuff;

import java.util.Vector;

public class Neuron {
	//the number of inputs into the neuron

	   int numInputs;


	   //the weights for each input

	   Vector<Double> vecWeights;
	   
	   public Neuron(int numInputs) {
		   this.numInputs=numInputs;
		   vecWeights=new Vector<Double>();
		   for (int i = 0; i < numInputs + 1; i++) {//+1 because the threshold.
			vecWeights.add(1.017*Math.random());
		   }
	}
	   public int getNumInputs() {
		return numInputs;
	}
	public void setNumInputs(int numInputs) {
		this.numInputs = numInputs;
	}
	public Vector<Double> getVecWeights() {
		return new Vector<Double>(vecWeights);
	}
	public void setVecWeights(Vector<Double> vecWeights) {
		this.vecWeights = new Vector<Double>(vecWeights);
	}
	public Neuron(Vector<Double>weights) {
		vecWeights=new Vector<Double>(weights);
		numInputs=vecWeights.size()-1;
	}
}

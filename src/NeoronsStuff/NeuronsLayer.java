package NeoronsStuff;

import java.util.Vector;

public class NeuronsLayer {
	Vector<Neuron> neurons;
	int numOfNeurons;
	public NeuronsLayer(int numNeurons, int numInputsPerNeuron) {
		numOfNeurons=numNeurons;
		neurons=new Vector<Neuron>();
		for (int i = 0; i < numOfNeurons; i++) {
			neurons.add(new Neuron(numInputsPerNeuron));
		}
	}
	public NeuronsLayer(Vector<Neuron> neurons) {
		this.neurons=new Vector<Neuron>(neurons);
		numOfNeurons=neurons.size();
	}
}

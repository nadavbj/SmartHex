package NeoronsStuff;

import java.util.Vector;

import com.sun.org.apache.regexp.internal.recompile;

public class NeuralNet {
	int numInputs;

	int numOutputs;

	int numHiddenLayers;

	int neuronsPerHiddenLyr;

	 public NeuralNet() {
		numInputs=Params.numInputs;
		numOutputs=Params.numOfOutPuts;
		numHiddenLayers=Params.numHiddenLayers;
		neuronsPerHiddenLyr=Params.neuronsPerHiddenLyr;
		vecLayers=new Vector<NeuronsLayer>();
		//create the layers of the network
		if (numHiddenLayers > 0)
		{
			//create first hidden layer
		  vecLayers.add(new NeuronsLayer(neuronsPerHiddenLyr, numInputs));
	    
	    for (int i=0; i<numHiddenLayers-1; ++i)
	    {

				vecLayers.add(new NeuronsLayer(neuronsPerHiddenLyr,neuronsPerHiddenLyr));
	    }

	    //create output layer
		  vecLayers.add(new NeuronsLayer(numOutputs, neuronsPerHiddenLyr));
		}

	  else
	  {
		  //create output layer
		  vecLayers.add(new NeuronsLayer(numOutputs, numInputs));
	  }
	}

	  //storage for each layer of neurons including the output layer

	  Vector<NeuronsLayer> vecLayers;
	  
	  
	  public Vector<Double> GetWeights(){
		  Vector<Double>weights=new Vector<Double>();
		  for (int i = 0; i < vecLayers.size() ; i++) {
			for (int j = 0; j < vecLayers.get(i).neurons.size(); j++) {
				weights.addAll(vecLayers.get(i).neurons.get(j).vecWeights);
			}
		}
		  return weights;
	  }

	  

	  //returns the total number of weights in the net

	  public int GetNumberOfWeights(){
		  return GetWeights().size();
	  }

	 

	  //replaces the weights with new ones

	  public void PutWeights(Vector<Double> weights){
		  int weightIndex=0;
		  try{
		  for (int i = 0; i < vecLayers.size() ; i++) {
				for (int j = 0; j < vecLayers.get(i).neurons.size(); j++) {
					vecLayers.get(i).neurons.get(j).vecWeights.clear();
					for (int k = 0; k < vecLayers.get(i).neurons.get(j).numInputs+1; k++) {
						vecLayers.get(i).neurons.get(j).vecWeights.add(weights.get(weightIndex++));
					}
				}
			}
		  }
		  catch(Exception e){
			  e.printStackTrace();
		  }
	  }

	 

	  //calculates the outputs from a set of inputs

	  public Vector<Double> Update(Vector<Double> inputs){
		  Vector<Double> outputs=new Vector<Double>();
		  if(inputs.size()!=numInputs)
			  return outputs;
		  for (int i = 0; i < vecLayers.size(); i++) {
			if(i>0)
				inputs=new Vector<Double>(outputs);
			outputs.clear();
			for (int j = 0; j < vecLayers.get(i).numOfNeurons; j++) {
				int sum=0;
				for (int k = 0; k < vecLayers.get(i).neurons.get(j).numInputs; k++) {
					sum+=inputs.get(k)*vecLayers.get(i).neurons.get(j).vecWeights.get(k);
				}
				sum-=Params.bias*inputs.get(vecLayers.get(i).neurons.get(j).numInputs-1)*vecLayers.get(i).neurons.get(j).vecWeights.get(vecLayers.get(i).neurons.get(j).numInputs-1);
				outputs.add(Sigmoid(sum, Params.activationResponse));
			}
		}
		  
		  return outputs;
	  }

	 

	  //sigmoid response curve

	  public double Sigmoid(double activation, double response){
		  activation-=0.5;
		  return 1/(1+Math.exp((-1)*(activation) / response));
	  }
}

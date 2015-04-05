package GeneticsStuff;

import java.util.Vector;

public class Genome{
	public Vector <Double>	vecWeights;

	int          fitness;
	
	public Genome(Vector <Double>	vecWeights,int fitness) {
		this.vecWeights=new Vector<Double>(vecWeights);
		this.fitness=fitness;
	}
	public Genome(Vector <Double>	vecWeights) {
		this(vecWeights,0);
	}
	public void increaseFittness(int amount) {
		fitness+=amount;
	}
}

package GeneticsStuff;

import java.util.Comparator;
import java.util.Vector;

import NeoronsStuff.Neuron;
import NeoronsStuff.Params;

public class GeneticAlgorythm {
	//this holds the entire population of chromosomes
	public Vector <Genome> genPop	 ;

	//size of population
	int popSize;

	//amount of weights per chromo
	int chromoLength;

	//total fitness of population
	double totalFitness;

	//best fitness this population
	double bestFitness;

	//average fitness
	double averageFitness;

	//worst
	double worstFitness;

	//keeps track of the best genome
	int		fittestGenome;

	//probability that a chromosones bits will mutate.
	//Try figures around 0.05 to 0.3 ish
	double mutationRate;

	//probability of chromosones crossing over bits
	//0.7 is pretty good
	double crossoverRate;

	//generation counter
	int	  generation;


	public GeneticAlgorythm(int	  popsize, double	mutRat, double	crossRat,  int	  numweights) {
		this.popSize=popsize;
		mutationRate=mutRat;
		crossoverRate=crossRat;
		chromoLength=numweights;
		genPop=new Vector<Genome>();
		//initialise population with chromosomes consisting of random
		//weights and all fitnesses set to zero
		for (int i=0; i<popSize; ++i)
		{
			genPop.add(new Genome(new Neuron(chromoLength).getVecWeights()));
		}
	}
	public void mutate(Vector<Double> chromo) {
		//traverse the chromosome and mutate each weight dependent
		//on the mutation rate
		for (int i=0; i<chromo.size(); ++i)
		{
			//do we perturb this weight?
			if (Math.random() < mutationRate)
			{
				//add or subtract a small value to the weight
				chromo.set(i, chromo.get(i)+(Math.random() * Params.dMaxPerturbation)) ;
			}
		}
	}
	public Genome GetChromoRoulette()
	{
		//generate a random number between 0 & total fitness count
		double Slice = (double)(Math.random() * totalFitness);

		//this will be set to the chosen chromosome
		Genome TheChosenOne=null;

		//go through the chromosones adding up the fitness so far
		double FitnessSoFar = 0;

		for (int i=0; i<popSize; ++i)
		{
			FitnessSoFar += genPop.get(i).fitness;

			//if the fitness so far > random number return the chromo at 
			//this point
			if (FitnessSoFar >= Slice)
			{
				TheChosenOne = genPop.get(i);

				break;
			}

		}

		return TheChosenOne;
	}

	void Crossover( Vector<Double> mum,
			Vector<Double> dad,
			Vector<Double> baby1,
			Vector<Double> baby2)
	{
		//just return parents as offspring dependent on the rate
		//or if parents are the same
		if ( (Math.random() > crossoverRate) || (mum == dad)) 
		{
			baby1 = mum;
			baby2 = dad;

			return;
		}

		//determine a crossover point
		double cp = Math.random()*crossoverRate;

		for (int i = 0; i < mum.size(); i++){

			baby1.add(mum.get(i) * cp + dad.get(i) * (cp - 1));
			baby2.add(mum.get(i) * (cp - 1) + dad.get(i) * cp);
		}
		return;
	}
	//-----------------------------------Epoch()-----------------------------
	//
//		takes a population of chromosones and runs the algorithm through one
//		 cycle.
//		Returns a new population of chromosones.
	//
	//-----------------------------------------------------------------------
	Vector<Genome> Epoch(Vector<Genome> old_pop)
	{
		//assign the given population to the classes population
	  genPop = old_pop;

	  //reset the appropriate variables
	  Reset();

	  genPop.sort(new Comparator<Genome>() {

		@Override
		public int compare(Genome arg0, Genome arg1) {
			return arg0.fitness-arg1.fitness;
		}
	});

	  //calculate best, worst, average and total fitness
		CalculateBestWorstAvTot();
	  
	  //create a temporary Vector to store new chromosones
		Vector <Genome> vecNewPop=new Vector<Genome>();

		//Now to add a little elitism we shall add in some copies of the
		//fittest genomes. Make sure we add an EVEN number or the roulette
	  //wheel sampling will crash
		if ((Params.iNumCopiesElite * Params.iNumElite % 2)==0)
		{
			GrabNBest(Params.iNumElite, Params.iNumCopiesElite, vecNewPop);
		}
		

		//now we enter the GA loop
		
		//repeat until a new population is generated
		while (vecNewPop.size() < popSize)
		{
			//grab two chromosones
			Genome mum = GetChromoRoulette();
			Genome dad = GetChromoRoulette();

			//create some offspring via crossover
			Vector<Double>		baby1 = new Vector<Double>(), baby2=new Vector<Double>();

			Crossover(mum.vecWeights, dad.vecWeights, baby1, baby2);

			//now we mutate
			mutate(baby1);
			mutate(baby2);

			//now copy into vecNewPop population
			vecNewPop.add(new Genome(baby1, 0));
			vecNewPop.add(new Genome(baby2, 0));
		}

		//finished so assign new pop back into genPop
		genPop = vecNewPop;

		return genPop;
	}


	//-------------------------GrabNBest----------------------------------
	//
//		This works like an advanced form of elitism by inserting NumCopies
	//  copies of the NBest most fittest genomes into a population Vector
	//--------------------------------------------------------------------
	void GrabNBest(int  NBest,int NumCopies,
	                        Vector<Genome>	pop)
	{
	  //add the required amount of copies of the n most fittest 
		//to the supplied Vector
		while(NBest-->0)
		{
			for (int i=0; i<NumCopies; ++i)
			{
				pop.add(genPop.get((popSize - 1) - NBest));
		  }
		}
	}

	//-----------------------CalculateBestWorstAvTot-----------------------	
	//
//		calculates the fittest and weakest genome and the average/total 
//		fitness scores
	//---------------------------------------------------------------------
	void CalculateBestWorstAvTot()
	{
		totalFitness = 0;
		
		double HighestSoFar = -9999999;
		double LowestSoFar  = 9999999;
		
		for (int i=0; i<popSize; ++i)
		{
			//update fittest if necessary
			if (genPop.get(i).fitness > HighestSoFar)
			{
				HighestSoFar	 = genPop.get(i).fitness;
				
				fittestGenome = i;

				bestFitness	 = HighestSoFar;
			}
			
			//update worst if necessary
			if (genPop.get(i).fitness < LowestSoFar)
			{
				LowestSoFar = genPop.get(i).fitness;
				
				worstFitness = LowestSoFar;
			}
			
			totalFitness	+= genPop.get(i).fitness;
			
			
		}//next chromo
		
		averageFitness = totalFitness / popSize;
	}

	//-------------------------Reset()------------------------------
	//
//		resets all the relevant variables ready for a new generation
	//--------------------------------------------------------------
	void Reset()
	{
		totalFitness		= 0;
		bestFitness		= 0;
		worstFitness		= 9999999;
		averageFitness	= 0;
	}
	public void moveGeneration() {
		generation++;
		genPop=Epoch(genPop);
	}
}

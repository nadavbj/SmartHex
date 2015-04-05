import java.util.Vector;

import GeneticsStuff.GeneticAlgorythm;
import Hex.Board;
import Hex.Board.color;
import NeoronsStuff.NeuralNet;
import NeoronsStuff.Params;


public class mainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board board=new Board();
		NeuralNet blue=new NeuralNet();
		NeuralNet red=new NeuralNet();
		GeneticAlgorythm bluePlayers =	new GeneticAlgorythm(5, 0.2, 0.7, blue.GetNumberOfWeights());
		GeneticAlgorythm redPlayers  =	new GeneticAlgorythm(5, 0.2, 0.7, red.GetNumberOfWeights());

		//learn how to play trough 10000 generations 
		for (int i = 1; i <= 10000; i++) {
			System.out.println("gen: "+i);
			for (int bluePlayer = 0; bluePlayer < bluePlayers.genPop.size(); bluePlayer++) {
				for (int redPlayer = 0; redPlayer < redPlayers.genPop.size(); redPlayer++) {
					blue.PutWeights(bluePlayers.genPop.get(bluePlayer).vecWeights);
					red.PutWeights(redPlayers.genPop.get(redPlayer).vecWeights);
					board.resetBoard();
					while(true){
						if(board.update(blue.Update(board.representByVector()), color.blue))
						{
							bluePlayers.genPop.get(bluePlayer).increaseFittness(1);
						}
						else
						{
							System.out.println("fail\n\n\n");
							bluePlayers.genPop.get(bluePlayer).increaseFittness(-1);
							break;
						}
						if(board.isWinner(color.blue))
						{
							System.out.println("blue won");
							bluePlayers.genPop.get(bluePlayer).increaseFittness(10);
							redPlayers.genPop.get(redPlayer).increaseFittness(-3);
							break;
						}
						if(board.update(red.Update(board.representByVector()), color.red))
						{
							redPlayers.genPop.get(redPlayer).increaseFittness(1);
						}
						else
						{
							System.out.println("fail\n\n\n");
							redPlayers.genPop.get(redPlayer).increaseFittness(-1);
							break;
						}
						if(board.isWinner(color.red))
						{
							System.out.println("red won");
							bluePlayers.genPop.get(bluePlayer).increaseFittness(-3);
							redPlayers.genPop.get(redPlayer).increaseFittness(10);
							break;
						}
					}
				}
			}
			bluePlayers.moveGeneration();
			redPlayers.moveGeneration();
		}



		while(true){
			if(!board.update(blue.Update(board.representByVector()), color.blue))
			{
				System.out.println("fail");
				/*Vector<Double> cords=new Vector<Double>();
				for (int i = 0; i < 6; i++) {
					cords.add(1/(1+Math.exp((-1)*(Math.random()-0.5) / 0.05)));
				}
				board.update(cords, color.blue);*/
				blue=new NeuralNet();
			}
			if(board.isWinner(color.blue))
			{
				System.out.println("blue won");
				break;
			}
			if(!board.update(red.Update(board.representByVector()), color.red))
			{
				System.out.println("fail");
				/*Vector<Double> cords=new Vector<Double>();
				for (int i = 0; i < 6; i++) {
					cords.add(1/(1+Math.exp((-1)*(Math.random()-0.5) / 0.05)));
				}
				board.update(cords, color.red);*/
				red=new NeuralNet();
			}
			if(board.isWinner(color.red))
			{
				System.out.println("red won");
				break;
			}

		}


	}

}

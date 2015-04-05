package Hex;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JFrame;

public class Board extends JFrame{
	static final int size=8,windowSize=800,hexSize=60;
	public static enum color {empty,blue,red};
	color[][]board;
	static final byte dirMat[][]={{ 0, 1,1,0,-1,-1},
		   {-1,-1,0,1, 1, 0}};
	private boolean [][]checkedHexs;

	public Board() {
		setVisible(true);
		setSize(windowSize, windowSize);
		board=new color[size][size];
		checkedHexs=new boolean[size][size];
		resetBoard();
	}
	public void resetBoard() {
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
			{
				board[i][j]=color.empty;
				if(i==0||i==size-1)
					board[i][j]=color.red;
				if(j==0||j==size-1)
					board[i][j]=color.blue;
			}
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				switch (board[i][j]) {
				case empty:
					g.setColor(Color.WHITE);
					break;
				case blue:
					g.setColor(Color.BLUE);
					break;
				case red:
					g.setColor(Color.RED);
					break;

				default:
					break;
				}
				g.fillPolygon(new int[]{i*(hexSize+2)+(hexSize/2+1)*j+20+0,i*(hexSize+2)+(hexSize/2+1)*j+20+0,i*(hexSize+2)+(hexSize/2+1)*j+20+hexSize/2,i*(hexSize+2)+(hexSize/2+1)*j+20+hexSize,i*(hexSize+2)+(hexSize/2+1)*j+20+hexSize,i*(hexSize+2)+(hexSize/2+1)*j+20+hexSize/2},
						new int[]{(int)(50+j*(hexSize+4)*0.75)+hexSize/4,50+(int)(j*(hexSize+4)*0.75)+(int)(hexSize*0.75),50+(int)(j*(hexSize+4)*0.75)+hexSize,50+(int)(j*(hexSize+4)*0.75)+(int)(hexSize*0.75),50+(int)(j*(hexSize+4)*0.75)+hexSize/4,50+(int)(j*(hexSize+4)*0.75)+0},6);
				g.setColor(Color.black);
				g.drawPolygon(new int[]{i*(hexSize+2)+(hexSize/2+1)*j+20+0,i*(hexSize+2)+(hexSize/2+1)*j+20+0,i*(hexSize+2)+(hexSize/2+1)*j+20+hexSize/2,i*(hexSize+2)+(hexSize/2+1)*j+20+hexSize,i*(hexSize+2)+(hexSize/2+1)*j+20+hexSize,i*(hexSize+2)+(hexSize/2+1)*j+20+hexSize/2},
						new int[]{(int)(50+j*(hexSize+4)*0.75)+hexSize/4,50+(int)(j*(hexSize+4)*0.75)+(int)(hexSize*0.75),50+(int)(j*(hexSize+4)*0.75)+hexSize,50+(int)(j*(hexSize+4)*0.75)+(int)(hexSize*0.75),50+(int)(j*(hexSize+4)*0.75)+hexSize/4,50+(int)(j*(hexSize+4)*0.75)+0},6);

			}
		}
	}
	public Vector<Double> representByVector() {
		Vector<Double> chromosone=new Vector<Double>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				switch (board[i][j]) {
				case empty:
					chromosone.add(0.0);
					chromosone.add(0.0);
					break;
				case blue:
					chromosone.add(1.0);
					chromosone.add(0.0);
					break;
				case red:
					chromosone.add(1.0);
					chromosone.add(1.0);
					break;

				default:
					break;
				}
			}
		}
		return chromosone;
	}
	public boolean update(Vector<Double> chromosone, color player) {
		/*int index=0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				switch ((int)Math.round(chromosone.get(index++)+chromosone.get(index++))) {
				case 0:
					board[i][j]=color.empty;
					break;
				case 1:
					board[i][j]=color.blue;
					break;
				case 2:
					board[i][j]=color.red;
					break;

				default:
					break;
				}
			}
		}*/
		for (int i = 0; i < chromosone.size(); i++) {
			System.out.print(chromosone.get(i)+"-->");
		}
		System.out.println();
		int x,y;
		x=(int) (4*Math.round(chromosone.get(0))+2*Math.round(chromosone.get(1))+Math.round(chromosone.get(2)));
		y=(int) (4*Math.round(chromosone.get(3))+2*Math.round(chromosone.get(4))+Math.round(chromosone.get(5)));
		if(board[x][y]!=color.empty)
			return false;
		board[x][y]=player;
		repaint();
		return true;
	}
	
	public boolean isWinner(color player) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				checkedHexs[i][j]=true;
			}
		}
			if(player==color.red)
				return areConected(0,1,size-1,1);
			else
				return areConected(1,0,1,size-1);
	}
	private boolean areConected(int xSource, int ySource, int xDest, int yDest) {
		if(xSource==xDest&&ySource==yDest)
			return true;
		checkedHexs[xSource][ySource]=false;
		for (int i = 0; i < dirMat[0].length; i++) {
			if(xSource+dirMat[0][i] >= 0 && xSource+dirMat[0][i] < size && ySource+dirMat[1][i] >= 0 && ySource+dirMat[1][i] < size){
				if(board[xSource][ySource]==board[xSource+dirMat[0][i]][ySource+dirMat[1][i]] && checkedHexs[xSource+dirMat[0][i]][ySource+dirMat[1][i]])
					if(areConected(xSource+dirMat[0][i], ySource+dirMat[1][i], xDest, yDest))
						return true;
			}
		}
		return false;
	}
}


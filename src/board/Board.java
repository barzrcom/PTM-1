package board;

import java.io.Serializable;

public class Board implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 40L;
	final char[][] board;
	private int size = Integer.MAX_VALUE;
	
	public Board(char[][] grid){
		this.board = grid;
		this.updateBoardSize();
	}
	public char[][] getBoard() {
		return board;
	}
	@Override
	public int hashCode() {
		return java.util.Arrays.deepHashCode(board);
	}
	public void printState() {
		for (int i=0; i < this.board.length; i++) {
			System.out.println(this.board[i]);
		}
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	private void updateBoardSize() {
		/* 
		 * Set Board Size by counting his number of cells. X*Y
		 */
		if (this.size != Integer.MAX_VALUE) {
			int size = this.board.length * this.board[0].length;
			this.setSize(size);
		}
	}
}

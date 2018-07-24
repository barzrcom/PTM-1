package board;

import java.io.Serializable;

public class Board implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 40L;
	final char[][] board;
	
	public Board(char[][] grid){
		this.board = grid;
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
}

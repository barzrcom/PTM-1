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
	public int getBoardX() {
		return board[0].length;
	}
	public int getBoardY() {
		return board.length;
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

package board;

public class Board {
	final char[][] board;
	
	public Board(char[][] grid){
		this.board = grid;
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
	public char[][] getBoard() {
		return board;
	}
}

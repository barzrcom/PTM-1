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
	public char[][] getBoard() {
		return board;
	}
}

package board;

public class PipeGrid {
	final char[][] grid;
	
	public PipeGrid(char[][] grid){
		this.grid = grid;
	}
	@Override
	public int hashCode() {
		return java.util.Arrays.deepHashCode(grid);
	}
	public char[][] getGrid() {
		return grid;
	}
}

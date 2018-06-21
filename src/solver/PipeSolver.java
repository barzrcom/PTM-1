package solver;

import board.GameBoard;
import board.SearchableGameBoard;
import board.Solution;
import searcher.Searcher;

public class PipeSolver implements Solver {
	private Searcher searcher;

	public PipeSolver(Searcher searcher) {
		this.searcher = searcher;
	}

	@Override
	public Solution solve(GameBoard problem) {
		SearchableGameBoard sGB = new SearchableGameBoard(problem);
		return searcher.search(sGB);
	}
}

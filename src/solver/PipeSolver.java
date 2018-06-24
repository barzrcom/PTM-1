package solver;

import board.PipeGameBoard;
import board.SearchablePipeGameBoard;
import board.Solution;
import searcher.Searcher;

public class PipeSolver implements Solver {
	private Searcher searcher;

	public PipeSolver(Searcher searcher) {
		this.searcher = searcher;
	}

	@Override
	public Solution solve(PipeGameBoard problem) {
		SearchablePipeGameBoard sGB = new SearchablePipeGameBoard(problem);
		return searcher.search(sGB);
	}
}

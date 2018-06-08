package server;

public class MySolver implements Solver {
	private Searcher searcher;

	MySolver(Searcher searcher) {
		this.searcher = searcher;
	}

	@Override
	public Solution solve(GameBoard problem) {
		SearchableGameBoard sGB = new SearchableGameBoard(problem);
		return searcher.search(sGB);
	}
}

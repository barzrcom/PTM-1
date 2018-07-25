package searcher;

import board.Solution;

public interface Searcher {
	public void reset();
	public Solution search(Searchable<?> s);
}

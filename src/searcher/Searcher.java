package searcher;

import board.Solution;

public interface Searcher {
	public Solution search(Searchable<?> s);
	public void reset();
}

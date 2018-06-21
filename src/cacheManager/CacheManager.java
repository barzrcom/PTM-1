package cacheManager;

import board.State;

public interface CacheManager {
	public boolean isSolutionExist(State problem);
	public void save(State problem, State solution);
	public State load(State problem);
}

package cacheManager;

import board.Solution;
import board.State;

public interface CacheManager {
	public boolean isSolutionExist(State problem);
	public void save(State problem, Solution solution);
	public Solution load(State problem);
}

package cacheManager;

import board.Solution;
import board.State;

public interface CacheManager {
	public <T> boolean isSolutionExist(State<T> problem);
	public <T> void save(State<T> problem, Solution solution);
	public <T> Solution load(State<T> problem);
}

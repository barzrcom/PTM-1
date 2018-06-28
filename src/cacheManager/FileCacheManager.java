package cacheManager;

import board.Solution;
import board.State;

public class FileCacheManager implements CacheManager{

	@Override
	public <T> boolean isSolutionExist(State<T> problem) {
		return false;
	}

	@Override
	public <T> void save(State<T> problem, Solution solution) {
		
	}

	@Override
	public <T> Solution load(State<T> problem) {
		return null;
	}

	
}

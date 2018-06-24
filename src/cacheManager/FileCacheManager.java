package cacheManager;

import board.Solution;
import board.State;

public class FileCacheManager implements CacheManager{

	@Override
	public boolean isSolutionExist(State problem) {
		return false;
	}

	@Override
	public void save(State problem, Solution solution) {
		
	}

	@Override
	public Solution load(State problem) {
		return null;
	}

	
}

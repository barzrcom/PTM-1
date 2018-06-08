package server;

public class MyCacheManager implements CacheManager{

	@Override
	public boolean isSolutionExist(State problem) {
		// TODO Auto-generated method stub
		// Check if solve exists for given state
		return false;
	}

	@Override
	public void save(State problem, State solution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State load(State problem) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

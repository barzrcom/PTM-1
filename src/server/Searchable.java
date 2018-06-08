package server;

import java.util.List;

public interface Searchable {
	State getInitialState();
	boolean isGoalState(State state);
	List<State> getAllPossibleStates(State state);
}

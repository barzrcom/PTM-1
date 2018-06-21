package searcher;

import java.util.List;

import board.State;

public interface Searchable {
	State getInitialState();
	boolean isGoalState(State state);
	int grade(State state);
	List<State> getAllPossibleStates(State state);
}

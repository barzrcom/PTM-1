package searcher;

import java.util.List;

import board.State;

public interface Searchable {
	State getInitialState();
	boolean isGoalState(State state);
	List<State> getAllPossibleStates(State state);
}

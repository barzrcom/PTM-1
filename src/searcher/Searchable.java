package searcher;

import java.util.List;

import board.State;

public interface Searchable<T> {
	State<T> getInitialState();
	boolean isGoalState(State<T> state);
	int grade(State<T> state);
	List<State<T>> getAllPossibleStates(State<T> state);
}

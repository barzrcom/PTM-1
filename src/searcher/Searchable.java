package searcher;

import java.util.List;

import board.State;

public interface Searchable<T> {
	List<State<T>> getAllPossibleStates(State<T> state);
	State<T> getInitialState();
	double grade(State<T> state);
	boolean isGoalState(State<T> state);
}

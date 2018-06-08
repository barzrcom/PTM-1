package server;

import java.util.List;

public interface GameBoard {
	State getInitialState();
	boolean isGoalState(State state);
	List<State> getAllPossibleStates(State state);
}

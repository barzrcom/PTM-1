package board;

import java.util.List;

public interface GameBoard {
	State getInitialState();
	boolean isGoalState(State state);
	int maxStepsOfState(State state);
	List<State> getAllPossibleStates(State state);
}

package board;

import java.util.List;

import searcher.Searchable;

public class SearchableGameBoard implements Searchable {	
	private GameBoard gameBoard;

	public SearchableGameBoard(GameBoard gameBoard){
		this.gameBoard = gameBoard;
	}
	@Override
	public State getInitialState() {
		// TODO Auto-generated method stub
		return gameBoard.getInitialState();
	}

	@Override
	public boolean isGoalState(State state) {
		// TODO Auto-generated method stub
		return gameBoard.isGoalState(state);
	}

	@Override
	public List<State> getAllPossibleStates(State state) {
		// TODO Auto-generated method stub
		return gameBoard.getAllPossibleStates(state);
	}
	@Override
	public int grade(State state) {
		return gameBoard.maxStepsOfState(state);
	}

}

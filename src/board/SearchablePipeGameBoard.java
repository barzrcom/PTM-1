package board;

import java.util.List;

import searcher.Searchable;

public class SearchablePipeGameBoard implements Searchable<char[][]> {	
	private PipeGameBoard gameBoard;

	public SearchablePipeGameBoard(PipeGameBoard gameBoard){
		this.gameBoard = gameBoard;
	}
	@Override
	public State<char[][]> getInitialState() {
		// TODO Auto-generated method stub
		return gameBoard.getInitialState();
	}

	@Override
	public boolean isGoalState(State<char[][]> state) {
		// TODO Auto-generated method stub
		return gameBoard.isGoalState(state);
	}

	@Override
	public List<State<char[][]>> getAllPossibleStates(State<char[][]> state) {
		// TODO Auto-generated method stub
		return gameBoard.getAllPossibleStates(state);
	}
	@Override
	public int grade(State<char[][]> state) {
		return gameBoard.heuristicGrade(state);
	}

}

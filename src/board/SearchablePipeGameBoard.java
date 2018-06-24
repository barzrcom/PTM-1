package board;

import java.util.List;

import searcher.Searchable;

public class SearchablePipeGameBoard implements Searchable<Board> {	
	private PipeGameBoard gameBoard;

	public SearchablePipeGameBoard(PipeGameBoard gameBoard){
		this.gameBoard = gameBoard;
	}
	@Override
	public State<Board> getInitialState() {
		// TODO Auto-generated method stub
		return gameBoard.getInitialState();
	}

	@Override
	public boolean isGoalState(State<Board> state) {
		// TODO Auto-generated method stub
		return gameBoard.isGoalState(state);
	}

	@Override
	public List<State<Board>> getAllPossibleStates(State<Board> state) {
		// TODO Auto-generated method stub
		return gameBoard.getAllPossibleStates(state);
	}
	@Override
	public int grade(State<Board> state) {
		return gameBoard.heuristicGrade(state);
	}

}

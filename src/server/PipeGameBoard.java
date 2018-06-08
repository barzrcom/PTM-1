package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PipeGameBoard implements GameBoard {
	// TODO: move to abstract class
	private State initialState;
	
	PipeGameBoard(char[][] board) {
		// TODO: parse pipeBoard into State object and save it
		initialState = new State(board);
	}

	@Override
	public State getInitialState() {
		// TODO Auto-generated method stub
		return initialState;
	}

	@Override
	public boolean isGoalState(State state) {
		// TODO Auto-generated method stub
		
		// TODO: check if board state is correct (at least one flow between s to g)
		
		return false;
	}

	@Override
	public List<State> getAllPossibleStates(State state) {
		// TODO Auto-generated method stub
		List<State> arrList = new ArrayList<State>();

		char[][] state2D = state.getState();

		for (int i=0; i < state2D.length; i++) {
			for (int j=0; j < state2D[i].length; j++) {
				// copy our state
				char[][] newPossible = new char[state2D.length][state2D[i].length];
				for (int k=0; k < state2D.length; k++) {
					System.arraycopy(state2D[k], 0, newPossible[k], 0, state2D[k].length);
				}
				
				
				switch (state2D[i][j]) {
					case 'L':
						newPossible[i][j] = 'F';
						break;
					case 'F':
						newPossible[i][j] = '7';
						break;
					case '7':
						newPossible[i][j] = 'J';
						break;
					case 'J':
						newPossible[i][j] = 'L';
						break;
					case '-':
						newPossible[i][j] = '|';
						break;
					case '|':
						newPossible[i][j] = '-';
						break;
					default:
						continue;

				}
				arrList.add(new State(newPossible));
			}
		}
		
		
		return arrList;
	}
	
}

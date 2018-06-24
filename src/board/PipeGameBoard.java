package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.util.Pair;

public class PipeGameBoard {
	// TODO: move to abstract class
	private State<char[][]> initialState;
	private Point startIndex;
	private Point goalIndex;
	private int boardX;
	private int boardY;
	public enum directions {start, goal, right, left, up, down}
	public PipeGameBoard(char[][] board) {
		boardY = board.length;
		boardX = board[0].length;
		initialState = new State<char[][]>(board);
		// get start and end points
		for (int i=0; i < board.length; i++) {
			for (int j=0; j < board[i].length; j++) {
				if (board[i][j] == 's'){
					startIndex = new Point(i,j);
				}
				else if (board[i][j] == 's'){
					goalIndex = new Point(i,j);
				} 
			}
		}
	}
	public static char changePipe(char currentPipe) {
		switch (currentPipe) {
		case 'L':
			return 'F';
		case 'F':
			return '7';
		case '7':
			return 'J';
		case 'J':
			return 'L';
		case '-':
			return '|';
		case '|':
			return '-';
		case 's':
			return 's';
		case 'g':
			return 'g';
		case ' ':
			return ' ';
		default:
			return ' ';
		}
	}

	public State<char[][]> getInitialState() {
		// TODO Auto-generated method stub
		return initialState;
	}
	private boolean isGoalStateLogic(char[][] stateBoard, int posX, int posY, directions from) {
		//check bounds
		if (posX < 0 || posX >= boardX)
			return false;
		if (posY < 0 || posY >= boardY)
			return false;
		// start
		if (from == directions.start) {
			return (isGoalStateLogic(stateBoard, posX+1, posY, directions.left) || 
					isGoalStateLogic(stateBoard, posX-1, posY, directions.right) ||
					isGoalStateLogic(stateBoard, posX, posY+1, directions.up) ||
					isGoalStateLogic(stateBoard, posX, posY-1, directions.down));
		}
		switch (stateBoard[posY][posX]) {
		case 'g':
			return true;
		case '-':
			if(from == directions.left)
				return isGoalStateLogic(stateBoard, posX+1, posY, directions.left);
			else if(from == directions.right)
				return isGoalStateLogic(stateBoard, posX-1, posY, directions.right);
			else return false;
		case '|':
			if(from == directions.up)
				return isGoalStateLogic(stateBoard, posX, posY+1, directions.up);
			else if(from == directions.down)
				return isGoalStateLogic(stateBoard, posX, posY-1, directions.down);
			else return false;
		case 'L':
			if(from == directions.up)
				return isGoalStateLogic(stateBoard, posX+1, posY, directions.left);
			else if(from == directions.right)
				return isGoalStateLogic(stateBoard, posX, posY-1, directions.down);
			else return false;
		case 'F':
			if(from == directions.right)
				return isGoalStateLogic(stateBoard, posX, posY+1, directions.up);
			else if(from == directions.down)
				return isGoalStateLogic(stateBoard, posX+1, posY, directions.left);
			else return false;
		case '7':
			if(from == directions.left)
				return isGoalStateLogic(stateBoard, posX, posY+1, directions.up);
			else if(from == directions.down)
				return isGoalStateLogic(stateBoard, posX-1, posY, directions.right);
			else return false;
		case 'J':
			if(from == directions.up)
				return isGoalStateLogic(stateBoard, posX-1, posY, directions.right);
			else if(from == directions.left)
				return isGoalStateLogic(stateBoard, posX, posY-1, directions.down);
			else return false;
		}
		
		return false;
	}
	
	public boolean isGoalState(State<char[][]> state) {
		// TODO: check if board state is correct (at least one flow between s to g)
		return isGoalStateLogic(state.getState(), startIndex.x, startIndex.y, directions.start);
	}
	
	public void getAllPossibleStatesLogic(char[][] stateBoard, int posX, int posY, directions from, List<PipeStep> stepList) {
		//check bounds
		if (posX < 0 || posX >= boardX)
			return;
		if (posY < 0 || posY >= boardY)
			return;
		// start
		if (from == directions.start) {
			getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, stepList);
			getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, stepList);
			getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, stepList);
			getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, stepList);
			return;
		}
		switch (stateBoard[posY][posX]) {
		case 'g':
			stepList.add(new PipeStep(posX,posY));
			return;
		case '-':
			stepList.add(new PipeStep(posX,posY));
			if(from == directions.left)
				getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, stepList);
			else if(from == directions.right)
				getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, stepList);
			else return;
		case '|':
			stepList.add(new PipeStep(posX,posY));
			if(from == directions.up)
				 getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, stepList);
			else if(from == directions.down)
				 getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, stepList);
			else return;
		case 'L':
			stepList.add(new PipeStep(posX,posY));
			if(from == directions.up)
				getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, stepList);
			else if(from == directions.right)
				getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, stepList);
			else return;
		case 'F':
			stepList.add(new PipeStep(posX,posY));
			if(from == directions.right)
				getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, stepList);
			else if(from == directions.down)
				getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, stepList);
			else return;
		case '7':
			stepList.add(new PipeStep(posX,posY));
			if(from == directions.left)
				getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, stepList);
			else if(from == directions.down)
				getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, stepList);
			else return;
		case 'J':
			stepList.add(new PipeStep(posX,posY));
			if(from == directions.up)
				getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, stepList);
			else if(from == directions.left)
				getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, stepList);
			else return;
		}
		
		
		return;
	}
	
	
	public List<State<char[][]>> getAllPossibleStates(State<char[][]> state) {
		// TODO Auto-generated method stub
		List<PipeStep> stepList = new ArrayList<PipeStep>();
		getAllPossibleStatesLogic(state.getState(), startIndex.x, startIndex.y, directions.start, stepList);
		List<State<char[][]>> stateList = new ArrayList<State<char[][]>>();

		char[][] state2D = state.getState();
		for (PipeStep step : stepList) {
			char[][] newPossible = new char[state2D.length][state2D[0].length];
			for (int k=0; k < state2D.length; k++) {
				System.arraycopy(state2D[k], 0, newPossible[k], 0, state2D[k].length);
			}
			newPossible[step.y][step.x] = changePipe(state2D[step.y][step.x]);
			stateList.add(new State<char[][]>(newPossible, step));
		}	
		
		return stateList;
	}
	
	public static int max(Integer... vals) {
	    return Collections.max(Arrays.asList(vals)); 
	}
	
	private int boardGradeLogic(char[][] stateBoard, int posX, int posY, directions from) {	
		//check bounds
		if (posX < 0 || posX >= boardX)
			return 0;
		if (posY < 0 || posY >= boardY)
			return 0;
		// check if goal
		if(stateBoard[posY][posX] == 'g') return 1;
		// start
		if (from == directions.start) {
			return max(boardGradeLogic(stateBoard, posX+1, posY, directions.left), 
					boardGradeLogic(stateBoard, posX-1, posY, directions.right),
					boardGradeLogic(stateBoard, posX, posY+1, directions.up),
					boardGradeLogic(stateBoard, posX, posY-1, directions.down));
		}
		if (stateBoard[posY][posX] == '-') {
			if(from == directions.left)
				return 1 + boardGradeLogic(stateBoard, posX+1, posY, directions.left);
			else if(from == directions.right)
				return 1 + boardGradeLogic(stateBoard, posX-1, posY, directions.right);
			else return 0;
		}
		if (stateBoard[posY][posX] == '|') {
			if(from == directions.up)
				return 1 + boardGradeLogic(stateBoard, posX, posY+1, directions.up);
			else if(from == directions.down)
				return 1 + boardGradeLogic(stateBoard, posX, posY-1, directions.down);
			else return 0;
		}
		if (stateBoard[posY][posX] == '|') {
			if(from == directions.up)
				return 1 + boardGradeLogic(stateBoard, posX, posY+1, directions.up);
			else if(from == directions.down)
				return 1 + boardGradeLogic(stateBoard, posX, posY-1, directions.down);
			else return 0;
		}
		if (stateBoard[posY][posX] == 'L') {
			if(from == directions.up)
				return 1 + boardGradeLogic(stateBoard, posX+1, posY, directions.left);
			else if(from == directions.right)
				return 1 + boardGradeLogic(stateBoard, posX, posY-1, directions.down);
			else return 0;
		}
		if (stateBoard[posY][posX] == 'F') {
			if(from == directions.right)
				return 1 + boardGradeLogic(stateBoard, posX, posY+1, directions.up);
			else if(from == directions.down)
				return 1 + boardGradeLogic(stateBoard, posX+1, posY, directions.left);
			else return 0;
		}
		if (stateBoard[posY][posX] == '7') {
			if(from == directions.left)
				return 1 + boardGradeLogic(stateBoard, posX, posY+1, directions.up);
			else if(from == directions.down)
				return 1 + boardGradeLogic(stateBoard, posX-1, posY, directions.right);
			else return 0;
		}
		if (stateBoard[posY][posX] == 'J') {
			if(from == directions.up)
				return 1 + boardGradeLogic(stateBoard, posX-1, posY, directions.right);
			else if(from == directions.left)
				return 1 + boardGradeLogic(stateBoard, posX, posY-1, directions.down);
			else return 0;
		}
		
		return 0;
	}


	public int heuristicGrade(State<char[][]> state) {
		return (boardX*boardY)-boardGradeLogic(state.getState(), startIndex.x, startIndex.y, directions.start);
	}
	
}

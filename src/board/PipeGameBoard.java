package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class PipeGameBoard {
	public enum directions {down, goal, left, right, start, up}
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
	public static double max(Double... vals) {
	    return Collections.max(Arrays.asList(vals)); 
	}
	public static double min(Double... vals) {
	    return Collections.min(Arrays.asList(vals)); 
	}
	private int boardX;
	private int boardY;
	private Point goalIndex;
	// TODO: move to abstract class
	private State<Board> initialState;

	private Point startIndex;
	public PipeGameBoard(Board grid) {
		char[][] board = grid.getBoard();
		boardY = board.length;
		boardX = board[0].length;
		initialState = new State<Board>(grid);
		// get start and end points
		for (int i=0; i < board.length; i++) {
			for (int j=0; j < board[i].length; j++) {
				if (board[i][j] == 's'){
					startIndex = new Point(i,j);
				}
				else if (board[i][j] == 'g'){
					goalIndex = new Point(i,j);
				} 
			}
		}
	}
	
	private double boardGradeLogic(char[][] stateBoard, int posX, int posY, directions from) {	
		//check bounds
		if (posX < 0 || posX >= boardX)
			return 0;
		if (posY < 0 || posY >= boardY)
			return 0;
		// check if goal
		if(stateBoard[posY][posX] == 'g') return 0;
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
	
	public List<State<Board>> getAllPossibleStates(State<Board> state) {
		// TODO Auto-generated method stub
		char[][] state2D = state.getState().getBoard();  // original board

		List<char[][]> tempClonedStateList = new ArrayList<char[][]>();
		for (int i=0; i<state.neighborsPointList.size(); i++) {
			char[][] newPossible = new char[boardY][boardX];
			for (int k=0; k < boardY; k++) {
				System.arraycopy(state2D[k], 0, newPossible[k], 0, boardX);
			}
			tempClonedStateList.add(newPossible);
		}

		List<State<Board>> stateList = new ArrayList<State<Board>>();
		int i = 0;
		for (Point step : state.neighborsPointList) {
			tempClonedStateList.get(i)[step.y][step.x] = changePipe(state2D[step.y][step.x]);
			stateList.add(new State<Board>(new Board(tempClonedStateList.get(i)), new PipeStep(step)));
			i++;
		}

		return stateList;
	}
	
	
	public void getAllPossibleStatesLogic(char[][] stateBoard, int posX, int posY, directions from, HashSet<Point> stepList) {
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
			return;
		case '-':
			stepList.add(new Point(posX,posY));
			if(from == directions.left)
				getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, stepList);
			else if(from == directions.right)
				getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, stepList);
			else return;
		case '|':
			stepList.add(new Point(posX,posY));
			if(from == directions.up)
				 getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, stepList);
			else if(from == directions.down)
				 getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, stepList);
			else return;
		case 'L':
			stepList.add(new Point(posX,posY));
			if(from == directions.up)
				getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, stepList);
			else if(from == directions.right)
				getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, stepList);
			else return;
		case 'F':
			stepList.add(new Point(posX,posY));
			if(from == directions.right)
				getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, stepList);
			else if(from == directions.down)
				getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, stepList);
			else return;
		case '7':
			stepList.add(new Point(posX,posY));
			if(from == directions.left)
				getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, stepList);
			else if(from == directions.down)
				getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, stepList);
			else return;
		case 'J':
			stepList.add(new Point(posX,posY));
			if(from == directions.up)
				getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, stepList);
			else if(from == directions.left)
				getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, stepList);
			else return;
		}
		return;
	}
	
	public State<Board> getInitialState() {
		// TODO Auto-generated method stub
		return initialState;
	}
	
	public double heuristicGrade(State<Board> state) {
		return (boardX*boardY)-boardGradeLogic(state.getState().getBoard(), startIndex.x, startIndex.y, directions.start);
	}
	
	public boolean isGoalState(State<Board> state) {
		// TODO: check if board state is correct (at least one flow between s to g)
		return isGoalStateLogic(state, startIndex.x, startIndex.y, directions.start);
	}


	private boolean isGoalStateLogic(State<Board> stateBoard, int posX, int posY, directions from) {
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
		switch (stateBoard.getState().getBoard()[posY][posX]) {
		case 'g':
			return true;
		case '-':
			stateBoard.neighborsPointList.add(new Point(posX,posY));
			if(from == directions.left)
				return isGoalStateLogic(stateBoard, posX+1, posY, directions.left);
			else if(from == directions.right)
				return isGoalStateLogic(stateBoard, posX-1, posY, directions.right);
			else return false;
		case '|':
			stateBoard.neighborsPointList.add(new Point(posX,posY));
			if(from == directions.up)
				return isGoalStateLogic(stateBoard, posX, posY+1, directions.up);
			else if(from == directions.down)
				return isGoalStateLogic(stateBoard, posX, posY-1, directions.down);
			else return false;
		case 'L':
			stateBoard.neighborsPointList.add(new Point(posX,posY));
			if(from == directions.up)
				return isGoalStateLogic(stateBoard, posX+1, posY, directions.left);
			else if(from == directions.right)
				return isGoalStateLogic(stateBoard, posX, posY-1, directions.down);
			else return false;
		case 'F':
			stateBoard.neighborsPointList.add(new Point(posX,posY));
			if(from == directions.right)
				return isGoalStateLogic(stateBoard, posX, posY+1, directions.up);
			else if(from == directions.down)
				return isGoalStateLogic(stateBoard, posX+1, posY, directions.left);
			else return false;
		case '7':
			stateBoard.neighborsPointList.add(new Point(posX,posY));
			if(from == directions.left)
				return isGoalStateLogic(stateBoard, posX, posY+1, directions.up);
			else if(from == directions.down)
				return isGoalStateLogic(stateBoard, posX-1, posY, directions.right);
			else return false;
		case 'J':
			stateBoard.neighborsPointList.add(new Point(posX,posY));
			if(from == directions.up)
				return isGoalStateLogic(stateBoard, posX-1, posY, directions.right);
			else if(from == directions.left)
				return isGoalStateLogic(stateBoard, posX, posY-1, directions.down);
			else return false;
		}
		
		return false;
	}
	
}

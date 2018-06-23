package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.util.Pair;

public class PipeGameBoard implements GameBoard {
	// TODO: move to abstract class
	private State initialState;
	private Point startIndex;
	private Point goalIndex;
	private int boardX;
	private int boardY;
	public enum directions {start, goal, right, left, up, down}
	public PipeGameBoard(char[][] board) {
		boardY = board.length;
		boardX = board[0].length;
		initialState = new State(board);
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
	@Override
	public State getInitialState() {
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
	
	@Override
	public boolean isGoalState(State state) {
		// TODO: check if board state is correct (at least one flow between s to g)
		return isGoalStateLogic(state.getState(), startIndex.x, startIndex.y, directions.start);
	}
	
	public void getAllPossibleStatesLogic(char[][] stateBoard, int posX, int posY, directions from, List<Point> pointList) {
		//check bounds
		if (posX < 0 || posX >= boardX)
			return;
		if (posY < 0 || posY >= boardY)
			return;
		// start
		if (from == directions.start) {
			getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, pointList);
			getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, pointList);
			getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, pointList);
			getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, pointList);
			return;
		}
		switch (stateBoard[posY][posX]) {
		case 'g':
			pointList.add(new Point(posX,posY));
			return;
		case '-':
			pointList.add(new Point(posX,posY));
			if(from == directions.left)
				getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, pointList);
			else if(from == directions.right)
				getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, pointList);
			else return;
		case '|':
			pointList.add(new Point(posX,posY));
			if(from == directions.up)
				 getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, pointList);
			else if(from == directions.down)
				 getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, pointList);
			else return;
		case 'L':
			pointList.add(new Point(posX,posY));
			if(from == directions.up)
				getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, pointList);
			else if(from == directions.right)
				getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, pointList);
			else return;
		case 'F':
			pointList.add(new Point(posX,posY));
			if(from == directions.right)
				getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, pointList);
			else if(from == directions.down)
				getAllPossibleStatesLogic(stateBoard, posX+1, posY, directions.left, pointList);
			else return;
		case '7':
			pointList.add(new Point(posX,posY));
			if(from == directions.left)
				getAllPossibleStatesLogic(stateBoard, posX, posY+1, directions.up, pointList);
			else if(from == directions.down)
				getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, pointList);
			else return;
		case 'J':
			pointList.add(new Point(posX,posY));
			if(from == directions.up)
				getAllPossibleStatesLogic(stateBoard, posX-1, posY, directions.right, pointList);
			else if(from == directions.left)
				getAllPossibleStatesLogic(stateBoard, posX, posY-1, directions.down, pointList);
			else return;
		}
		
		
		return;
	}
	
	
	@Override
	public List<State> getAllPossibleStates(State state) {
		// TODO Auto-generated method stub
		List<Point> pointList = new ArrayList<Point>();
		getAllPossibleStatesLogic(state.getState(), startIndex.x, startIndex.y, directions.start, pointList);
		List<State> stateList = new ArrayList<State>();

		char[][] state2D = state.getState();
		for (Point point : pointList) {
			char[][] newPossible = new char[state2D.length][state2D[0].length];
			for (int k=0; k < state2D.length; k++) {
				System.arraycopy(state2D[k], 0, newPossible[k], 0, state2D[k].length);
			}
			newPossible[point.y][point.x] = changePipe(state2D[point.y][point.x]);
			stateList.add(new State(newPossible, point));
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

	
	@Override
	public int heuristicGrade(State state) {
		return (boardX*boardY)-boardGradeLogic(state.getState(), startIndex.x, startIndex.y, directions.start);
	}
	
}

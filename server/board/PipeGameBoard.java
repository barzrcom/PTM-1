package board;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PipeGameBoard {
	public enum directions {down, left, right, start, up}

	public static char changePipe(char currentPipe, int clicks) {
		while (clicks-- != 0) {
			switch (currentPipe) {
				case 'L':
					currentPipe = 'F';
					break;
				case 'F':
					currentPipe = '7';
					break;
				case '7':
					currentPipe = 'J';
					break;
				case 'J':
					currentPipe = 'L';
					break;
				case '-':
					currentPipe = '|';
					break;
				case '|':
					currentPipe = '-';
					break;
				case 's':
					currentPipe = 's';
					break;
				case 'g':
					currentPipe = 'g';
					break;
				case ' ':
					currentPipe = ' ';
					break;
				default:
					currentPipe = ' ';
					break;
			}
		}
		return currentPipe;
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
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 's') {
					startIndex = new Point(j, i);
				} else if (board[i][j] == 'g') {
					goalIndex = new Point(j, i);
				}
			}
		}
	}

	private double boardGradeLogic(char[][] stateBoard, int posX, int posY, directions from) {
		//check bounds
		if (posX < 0 || posX >= boardX)
			return Double.MAX_VALUE;
		if (posY < 0 || posY >= boardY)
			return Double.MAX_VALUE;
		// check if goal
		if (stateBoard[posY][posX] == 'g') return 0;
		// start
		if (from == directions.start) {
			return min(boardGradeLogic(stateBoard, posX + 1, posY, directions.left),
					boardGradeLogic(stateBoard, posX - 1, posY, directions.right),
					boardGradeLogic(stateBoard, posX, posY + 1, directions.up),
					boardGradeLogic(stateBoard, posX, posY - 1, directions.down));
		}
		if (stateBoard[posY][posX] == '-') {
			if (from == directions.left)
				return boardGradeLogic(stateBoard, posX + 1, posY, directions.left);
			else if (from == directions.right)
				return boardGradeLogic(stateBoard, posX - 1, posY, directions.right);
			else return Math.abs(Point.distance(posX, posY, goalIndex.x, goalIndex.y));
		}
		if (stateBoard[posY][posX] == '|') {
			if (from == directions.up)
				return boardGradeLogic(stateBoard, posX, posY + 1, directions.up);
			else if (from == directions.down)
				return boardGradeLogic(stateBoard, posX, posY - 1, directions.down);
			else return Math.abs(Point.distance(posX, posY, goalIndex.x, goalIndex.y));
		}
		if (stateBoard[posY][posX] == 'L') {
			if (from == directions.up)
				return boardGradeLogic(stateBoard, posX + 1, posY, directions.left);
			else if (from == directions.right)
				return boardGradeLogic(stateBoard, posX, posY - 1, directions.down);
			else return Math.abs(Point.distance(posX, posY, goalIndex.x, goalIndex.y));
		}
		if (stateBoard[posY][posX] == 'F') {
			if (from == directions.right)
				return boardGradeLogic(stateBoard, posX, posY + 1, directions.up);
			else if (from == directions.down)
				return boardGradeLogic(stateBoard, posX + 1, posY, directions.left);
			else return Math.abs(Point.distance(posX, posY, goalIndex.x, goalIndex.y));
		}
		if (stateBoard[posY][posX] == '7') {
			if (from == directions.left)
				return boardGradeLogic(stateBoard, posX, posY + 1, directions.up);
			else if (from == directions.down)
				return boardGradeLogic(stateBoard, posX - 1, posY, directions.right);
			else return Math.abs(Point.distance(posX, posY, goalIndex.x, goalIndex.y));
		}
		if (stateBoard[posY][posX] == 'J') {
			if (from == directions.up)
				return boardGradeLogic(stateBoard, posX - 1, posY, directions.right);
			else if (from == directions.left)
				return boardGradeLogic(stateBoard, posX, posY - 1, directions.down);
			else return Math.abs(Point.distance(posX, posY, goalIndex.x, goalIndex.y));
		}

		return Double.MAX_VALUE;
	}

	public List<State<Board>> getAllPossibleStates(State<Board> state) {
		// TODO Auto-generated method stub
		char[][] state2D = state.getState().getBoard();  // original board

		List<char[][]> tempClonedStateList = new ArrayList<char[][]>();
		for (int i = 0; i < state.neighborsPointList.size(); i++) {
			char[][] newPossible = new char[boardY][boardX];
			for (int k = 0; k < boardY; k++) {
				System.arraycopy(state2D[k], 0, newPossible[k], 0, boardX);
			}
			tempClonedStateList.add(newPossible);
		}

		List<State<Board>> stateList = new ArrayList<State<Board>>();
		int i = 0;
		for (PipeStep step : state.neighborsPointList) {
			tempClonedStateList.get(i)[step.y][step.x] = changePipe(state2D[step.y][step.x], step.getCost());
			stateList.add(new State<Board>(new Board(tempClonedStateList.get(i)), step));
			i++;
		}

		return stateList;
	}

	public State<Board> getInitialState() {
		// TODO Auto-generated method stub
		return initialState;
	}

	public double heuristicGrade(State<Board> state) {
		return boardGradeLogic(state.getState().getBoard(), startIndex.x, startIndex.y, directions.start);
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
			return (isGoalStateLogic(stateBoard, posX + 1, posY, directions.left) ||
					isGoalStateLogic(stateBoard, posX - 1, posY, directions.right) ||
					isGoalStateLogic(stateBoard, posX, posY + 1, directions.up) ||
					isGoalStateLogic(stateBoard, posX, posY - 1, directions.down));
		}
		stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 1));

		switch (stateBoard.getState().getBoard()[posY][posX]) {
			case 'g':
				return true;
			case '-':
				if (from == directions.left)
					return isGoalStateLogic(stateBoard, posX + 1, posY, directions.left);
				else if (from == directions.right)
					return isGoalStateLogic(stateBoard, posX - 1, posY, directions.right);
				else {
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 1));
					return false;
				}
			case '|':
				if (from == directions.up)
					return isGoalStateLogic(stateBoard, posX, posY + 1, directions.up);
				else if (from == directions.down)
					return isGoalStateLogic(stateBoard, posX, posY - 1, directions.down);
				else {
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 1));
					return false;
				}
			case 'L':
				if (from == directions.up)
					return isGoalStateLogic(stateBoard, posX + 1, posY, directions.left);
				else if (from == directions.right)
					return isGoalStateLogic(stateBoard, posX, posY - 1, directions.down);
				else {
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 1));
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 2));
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 3));
					return false;
				}
			case 'F':
				if (from == directions.right)
					return isGoalStateLogic(stateBoard, posX, posY + 1, directions.up);
				else if (from == directions.down)
					return isGoalStateLogic(stateBoard, posX + 1, posY, directions.left);
				else {
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 1));
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 2));
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 3));
					return false;
				}
			case '7':
				if (from == directions.left)
					return isGoalStateLogic(stateBoard, posX, posY + 1, directions.up);
				else if (from == directions.down)
					return isGoalStateLogic(stateBoard, posX - 1, posY, directions.right);
				else {
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 1));
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 2));
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 3));
					return false;
				}
			case 'J':
				if (from == directions.up)
					return isGoalStateLogic(stateBoard, posX - 1, posY, directions.right);
				else if (from == directions.left)
					return isGoalStateLogic(stateBoard, posX, posY - 1, directions.down);
				else {
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 1));
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 2));
					stateBoard.neighborsPointList.add(new PipeStep(posX, posY, 3));
					return false;
				}
		}
		return false;
	}

}
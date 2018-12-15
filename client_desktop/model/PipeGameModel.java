package model;

import board.PipeGameBoard.directions;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


public class PipeGameModel implements GameModel {

	public ListProperty<char[]> board;
	public BooleanProperty isGoal;
	public SimpleListProperty<Point> flowPoints;

	public PipeGameModel() {
		this.board = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));

		this.board.addListener((observableValue, s, t1) -> {
			char[][] pipes = this.board.toArray(new char[this.board.size()][]);
			Point startIndex = null;
			for (int i = 0; i < pipes.length; i++) {
				for (int j = 0; j < pipes[i].length; j++) {
					if (pipes[i][j] == 's') {
						startIndex = new Point(j, i);
						break;
					}
				}
			}
			if (startIndex != null) {
				isGoal.set(false);
				flowPoints.clear();
				isGoalStateLogic(startIndex.x, startIndex.y, directions.start);
			}
		});
		this.isGoal = new SimpleBooleanProperty();
		this.flowPoints = new SimpleListProperty<Point>(FXCollections.observableArrayList(new LinkedHashSet<Point>()));
	}

	public void setInitializedBoard() {
		//		char[][] level = {
		//				{'s', '-', '-', '-', '7', 'J', 'L', 'F' , '7'},
		//				{'7', '7', '7', '7', '7', '7', '7', '7' , '7'},
		//				{'7', '7', '7', ' ', ' ', ' ', '7', '7' , '7'},
		//				{'7', '7', '7', '7', '|', '7', '7', '7' , '7'},
		//				{'7', '7', '7', '7', 'L', '7', '7', '7' , '7'},
		//				{'7', '7', '7', '7', '7', '7', '7', '7' , '7'},
		//				{'7', '7', '7', '7', '|', '7', '7', '7' , '7'},
		//				{'7', '7', '-', '-', '-', '-', '-', '-' , 'g'},
		//		};
		char[][] level = {
				{'s', '-', '|', '7'},
				{'7', '-', '-', 'g'},
		};
		this.board.addAll(level);


	}

	public void changePipe(int x, int y) {
		switch (this.board.get(y)[x]) {
			case 'L':
				this.board.get(y)[x] = 'F';
				break;
			case 'F':
				this.board.get(y)[x] = '7';
				break;
			case '7':
				this.board.get(y)[x] = 'J';
				break;
			case 'J':
				this.board.get(y)[x] = 'L';
				break;
			case '-':
				this.board.get(y)[x] = '|';
				break;
			case '|':
				this.board.get(y)[x] = '-';
				break;
			case 's':
				this.board.get(y)[x] = 's';
				break;
			case 'g':
				this.board.get(y)[x] = 'g';
				break;
			case ' ':
				this.board.get(y)[x] = ' ';
				break;
			default:
				this.board.get(y)[x] = ' ';
				break;
		}
		// have to use set and get to notify the bind
		this.board.set(y, this.board.get(y));
	}

	public void loadGame(String fileName) {
		List<char[]> mapBuilder = new ArrayList<char[]>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				mapBuilder.add(line.toCharArray());
			}
			this.board.setAll(mapBuilder.toArray(new char[mapBuilder.size()][]));
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveGame(File file) {
		try {
			PrintWriter outFile = new PrintWriter(file);
			for (int i = 0; i < this.board.size(); i++) {
				outFile.println(new String(this.board.get(i)));
			}
			outFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isGoalStateLogic(int posX, int posY, directions from) {
		//check bounds
		if (posX < 0 || posX >= board.get(0).length)
			return false;
		if (posY < 0 || posY >= board.size())
			return false;
		// start
		if (from == directions.start) {
			return (isGoalStateLogic(posX + 1, posY, directions.left) ||
					isGoalStateLogic(posX - 1, posY, directions.right) ||
					isGoalStateLogic(posX, posY + 1, directions.up) ||
					isGoalStateLogic(posX, posY - 1, directions.down));
		}

		switch (board.get(posY)[posX]) {
			case 'g':
				flowPoints.add(new Point(posX, posY));
				isGoal.set(true);
				return true;
			case '-':
				if (from == directions.left) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX + 1, posY, directions.left);
				} else if (from == directions.right) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX - 1, posY, directions.right);
				} else {
					return false;
				}
			case '|':
				if (from == directions.up) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY + 1, directions.up);
				} else if (from == directions.down) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY - 1, directions.down);
				} else {
					return false;
				}
			case 'L':
				if (from == directions.up) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX + 1, posY, directions.left);
				} else if (from == directions.right) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY - 1, directions.down);
				} else {
					return false;
				}
			case 'F':
				if (from == directions.right) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY + 1, directions.up);
				} else if (from == directions.down) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX + 1, posY, directions.left);
				} else {
					return false;
				}
			case '7':
				if (from == directions.left) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY + 1, directions.up);
				} else if (from == directions.down) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX - 1, posY, directions.right);
				} else {
					return false;
				}
			case 'J':
				if (from == directions.up) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX - 1, posY, directions.right);
				} else if (from == directions.left) {
					flowPoints.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY - 1, directions.down);
				} else {
					return false;
				}
		}
		return false;
	}
}

package model;

import board.PipeGameBoard.directions;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.List;


public class PipeGameModel implements GameModel {

	public char[][] cleanBoard;
	public ListProperty<char[]> board;
	public BooleanProperty isGoal;
	public ListProperty<Point> flowPoints;
	public IntegerProperty numOfSteps;
	public IntegerProperty secondsElapsed;

	Socket serverSocket;

	Timer timer = new Timer();

	// you have to supply new TimerTask for each new timer
	public TimerTask getNewTimerTask() {
		return new TimerTask() {
			public void run(){
				Platform.runLater(()-> secondsElapsed.set(secondsElapsed.get() + 1));
			}
		};
	}
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
		this.isGoal.addListener((observableValue, s, t1) -> {
			if (true == isGoal.get()) {
				this.timer.cancel();
				this.timer = null;
			} else {
				if (null == this.timer) {
					this.timer = new Timer();
					this.timer.scheduleAtFixedRate(getNewTimerTask(), 1000, 1000);
				}
			}
		});
		this.flowPoints = new SimpleListProperty<>(FXCollections.observableArrayList(new LinkedHashSet<Point>()));
		this.numOfSteps = new SimpleIntegerProperty(0);

		this.secondsElapsed = new SimpleIntegerProperty(0);
		this.timer.scheduleAtFixedRate(getNewTimerTask(), 1000, 1000);
	}
	
	public void setCleanBoard(char[][] board) {
		this.cleanBoard = new char[board.length][board[0].length];
		for (int k = 0; k < board.length; k++) {
			System.arraycopy(board[k], 0, this.cleanBoard[k], 0, board[0].length);
		}
	}
	
	public char[][] getCleanBoard() {
		char[][] cloneBoard = new char[this.cleanBoard.length][this.cleanBoard[0].length];
		for (int k = 0; k < this.cleanBoard.length; k++) {
			System.arraycopy(this.cleanBoard[k], 0, cloneBoard[k], 0, this.cleanBoard[0].length);
		}
		return cloneBoard;
	}
	
	public void setInitializedBoard() {
		char[][] level = {
				{'s', '-', '-', '-', '7', 'J', 'L', 'F' , '7'},
				{'7', '7', '7', '7', '7', '7', '7', '7' , '7'},
				{'7', '7', '7', ' ', ' ', ' ', '7', '7' , '7'},
				{'7', '7', '7', '7', '|', '7', '7', '7' , '7'},
				{'7', '7', '7', '7', 'L', '7', '7', '7' , '7'},
				{'7', '7', '7', '7', '7', '7', '7', '7' , '7'},
				{'7', '7', '7', '7', '|', '7', '7', '7' , '7'},
				{'7', '7', '-', '-', '-', '-', '-', '-' , 'g'},
		};
		setCleanBoard(level);
		this.board.addAll(level);
	}

	public void changePipe(int x, int y) {
		switch (this.board.get(y)[x]) {
			case 'L':
				this.board.get(y)[x] = 'F';
				numOfSteps.set(numOfSteps.get() + 1);
				break;
			case 'F':
				this.board.get(y)[x] = '7';
				numOfSteps.set(numOfSteps.get() + 1);
				break;
			case '7':
				this.board.get(y)[x] = 'J';
				numOfSteps.set(numOfSteps.get() + 1);
				break;
			case 'J':
				this.board.get(y)[x] = 'L';
				numOfSteps.set(numOfSteps.get() + 1);
				break;
			case '-':
				this.board.get(y)[x] = '|';
				numOfSteps.set(numOfSteps.get() + 1);
				break;
			case '|':
				this.board.get(y)[x] = '-';
				numOfSteps.set(numOfSteps.get() + 1);
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
				if (line.startsWith("time:")) {
					// load game time from file
					int time = Integer.parseInt(line.split(":")[1]);
					secondsElapsed.set(time);
				} else if (line.startsWith("step:")) {
					// load game steps from file
					int step = Integer.parseInt(line.split(":")[1]);
					numOfSteps.set(step);
				} else {
					mapBuilder.add(line.toCharArray());
				}
			}
			setCleanBoard(mapBuilder.toArray(new char[mapBuilder.size()][]));
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

			// save game steps and time from file
			outFile.println("time:" + secondsElapsed.get());
			outFile.println("step:" + numOfSteps.get());

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

	public void connect(String serverIp, String serverPort) throws IOException {
		this.serverSocket = new Socket(serverIp, Integer.parseInt(serverPort));
		System.out.println("Connected to server");

	}

	public void solve() throws IOException, InterruptedException {
		// verify the connection.
		if (this.serverSocket != null) {
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
			PrintWriter outToServer = new PrintWriter(this.serverSocket.getOutputStream());

			for (char[] line : this.board.get()) {
				// send board to the server.
				outToServer.println(line);
				outToServer.flush();
			}
			outToServer.println("done");
			outToServer.flush();

			String line;
			// receive the solution.
			while (!(line = inFromServer.readLine()).equals("done")) {
				// changePipe accordingly. (consider sleep)
				String[] moves = line.split(",");
				int y = Integer.parseInt(moves[0]);
				int x = Integer.parseInt(moves[1]);
				int move = Integer.parseInt(moves[2]);
				// start counting from 1, to skip 0 moves
				for (int i = 1; i <= move; i++) {
					Platform.runLater(()-> changePipe(x, y));
					Thread.sleep(100);
				}
			}
		}
	}
	
	public void reset() {
		secondsElapsed.set(0);
		numOfSteps.set(0);
		this.board.setAll(getCleanBoard());
	}
	
	public void exit() {
		System.out.println("Exiting..");
		timer.cancel();
		this.disconnect();
		System.exit(0);
	}

	public void disconnect() {
		// close the socket if exists.
		if (this.serverSocket != null) {
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;

public class PipeGameModel implements GameModel {

	public StringProperty board;

	public PipeGameModel() {
		this.board = new SimpleStringProperty();
	}

	public void setInitializedBoard() {
		this.board.set("s---7JLF7\n"
				+ "777777777\n"
				+ "777   777\n"
				+ "7777|7777\n"
				+ "7777L7777\n"
				+ "777777777\n"
				+ "7777|7777\n"
				+ "77------g\n");

	}

	public void changePipe(int x, int y) {
		StringBuilder tmpBoard = new StringBuilder(this.board.get());

		// calc index include line terminator
		int firstLineSize = tmpBoard.indexOf("\n") + 1;
		int idx = (y * firstLineSize) + x;

		switch (tmpBoard.charAt(idx)) {
			case 'L':
				tmpBoard.setCharAt(idx, 'F');
				break;
			case 'F':
				tmpBoard.setCharAt(idx, '7');
				break;
			case '7':
				tmpBoard.setCharAt(idx, 'J');
				break;
			case 'J':
				tmpBoard.setCharAt(idx, 'L');
				break;
			case '-':
				tmpBoard.setCharAt(idx, '|');
				break;
			case '|':
				tmpBoard.setCharAt(idx, '-');
				break;
			case 's':
				tmpBoard.setCharAt(idx, 's');
				break;
			case 'g':
				tmpBoard.setCharAt(idx, 'g');
				break;
			case ' ':
				tmpBoard.setCharAt(idx, ' ');
				break;
			default:
				tmpBoard.setCharAt(idx, ' ');
				break;
		}
		this.board.set(tmpBoard.toString());
	}

	public void loadGame(String fileName) {

		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));

			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line).append('\n');
			}
			this.board.set(stringBuilder.toString());
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveGame(File file) {
		try {
			PrintWriter outFile = new PrintWriter(file);

			outFile.print(this.board.get());

			outFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}

package model;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PipeGameModel implements GameModel {

	public ListProperty<char[]> board;

	public PipeGameModel() {
		this.board = new SimpleListProperty<char[]>(FXCollections.observableArrayList(new ArrayList<>()));
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
		this.board.addAll(level);


	}

	public void changePipe(int x, int y) {
		switch (this.board.get(y)[x]) {
			case 'L':
				this.board.get(y)[x] =  'F';
				break;
			case 'F':
				this.board.get(y)[x] =  '7';
				break;
			case '7':
				this.board.get(y)[x] =  'J';
				break;
			case 'J':
				this.board.get(y)[x] =  'L';
				break;
			case '-':
				this.board.get(y)[x] =  '|';
				break;
			case '|':
				this.board.get(y)[x] =  '-';
				break;
			case 's':
				this.board.get(y)[x] =  's';
				break;
			case 'g':
				this.board.get(y)[x] =  'g';
				break;
			case ' ':
				this.board.get(y)[x] =  ' ';
				break;
			default:
				this.board.get(y)[x] =  ' ';
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
			for (int i=0; i< this.board.size(); i++) {
				outFile.println(new String(this.board.get(i)));
			}
			outFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}

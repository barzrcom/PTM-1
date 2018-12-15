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

	public ListProperty<char[]> boardList;

	public PipeGameModel() {
		this.boardList = new SimpleListProperty<char[]>(FXCollections.observableArrayList(new ArrayList<>()));
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
		this.boardList.addAll(level);


	}

	public void changePipe(int x, int y) {
		switch (this.boardList.get(y)[x]) {
			case 'L':
				this.boardList.get(y)[x] =  'F';
				break;
			case 'F':
				this.boardList.get(y)[x] =  '7';
				break;
			case '7':
				this.boardList.get(y)[x] =  'J';
				break;
			case 'J':
				this.boardList.get(y)[x] =  'L';
				break;
			case '-':
				this.boardList.get(y)[x] =  '|';
				break;
			case '|':
				this.boardList.get(y)[x] =  '-';
				break;
			case 's':
				this.boardList.get(y)[x] =  's';
				break;
			case 'g':
				this.boardList.get(y)[x] =  'g';
				break;
			case ' ':
				this.boardList.get(y)[x] =  ' ';
				break;
			default:
				this.boardList.get(y)[x] =  ' ';
				break;
		}
		// have to use set and get to notify the bind
		this.boardList.set(y, this.boardList.get(y));
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
			this.boardList.setAll(mapBuilder.toArray(new char[mapBuilder.size()][]));
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveGame(File file) {
		try {
			PrintWriter outFile = new PrintWriter(file);
			for (int i=0; i< this.boardList.size(); i++) {
				outFile.println(new String(this.boardList.get(i)));
			}
			outFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}

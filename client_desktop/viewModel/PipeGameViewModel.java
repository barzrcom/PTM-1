package viewModel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.PipeGameModel;
import java.io.File;



public class PipeGameViewModel {

	PipeGameModel m;

	public ListProperty<char[]> board;
	public PipeGameViewModel(PipeGameModel m) {
		this.m = m;
		this.board = new SimpleListProperty<char[]>();
		this.board.bindBidirectional(this.m.board);
	}

	public void changePipe(int x, int y) {
		this.m.changePipe(x, y);
	}

	public void loadGame(String fileName) {
		this.m.loadGame(fileName);
	}

	public void saveGame(File file) {
		this.m.saveGame(file);
	}
}

package viewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import model.PipeGameModel;

import java.awt.*;
import java.io.File;

public class PipeGameViewModel {

	public ListProperty<char[]> board;
	public BooleanProperty isGoal;
	public ListProperty<Point> flowPoints;
	PipeGameModel m;

	public PipeGameViewModel(PipeGameModel m) {
		this.m = m;
		this.board = new SimpleListProperty<>();
		this.board.bindBidirectional(this.m.board);
		this.isGoal = new SimpleBooleanProperty();
		this.isGoal.bindBidirectional(this.m.isGoal);
		this.flowPoints = new SimpleListProperty<>();
		this.flowPoints.bindBidirectional(this.m.flowPoints);
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

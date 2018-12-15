package viewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import model.PipeGameModel;

import java.awt.Point;
import java.io.File;


public class PipeGameViewModel {

	PipeGameModel m;

	public ListProperty<char[]> board;
	public BooleanProperty isGoal;
	public ListProperty<Point> flowPoints;
	
	public PipeGameViewModel(PipeGameModel m) {
		this.m = m;
		this.board = new SimpleListProperty<char[]>();
		this.board.bindBidirectional(this.m.board);
		this.isGoal = new SimpleBooleanProperty();
		this.isGoal.bindBidirectional(this.m.isGoal);
		this.flowPoints = new SimpleListProperty<Point>();
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

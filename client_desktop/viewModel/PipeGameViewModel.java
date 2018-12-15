package viewModel;

import javafx.beans.property.*;
import model.PipeGameModel;

import java.awt.*;
import java.io.File;

public class PipeGameViewModel {

	public ListProperty<char[]> board;
	public BooleanProperty isGoal;
	public ListProperty<Point> flowPoints;
	public IntegerProperty numOfSteps;
	PipeGameModel m;

	public PipeGameViewModel(PipeGameModel m) {
		this.m = m;
		this.board = new SimpleListProperty<>();
		this.board.bind(this.m.board);
		this.isGoal = new SimpleBooleanProperty();
		this.isGoal.bind(this.m.isGoal);
		this.flowPoints = new SimpleListProperty<>();
		this.flowPoints.bind(this.m.flowPoints);
		this.flowPoints = new SimpleListProperty<>();
		this.flowPoints.bind(this.m.flowPoints);
		this.numOfSteps = new SimpleIntegerProperty();
		this.numOfSteps.bind(this.m.numOfSteps);
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

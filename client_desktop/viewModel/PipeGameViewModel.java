package viewModel;

import javafx.beans.property.*;
import model.PipeGameModel;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PipeGameViewModel {

	public ListProperty<char[]> board;
	public BooleanProperty isGoal;
	public ListProperty<Point> flowPoints;
	public IntegerProperty numOfSteps;
	public IntegerProperty secondsElapsed;
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
		this.numOfSteps.bindBidirectional(this.m.numOfSteps);
		this.secondsElapsed = new SimpleIntegerProperty();
		this.secondsElapsed.bind(this.m.secondsElapsed);
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

	public void connect(String serverIp, String serverPort) throws IOException {
		this.m.connect(serverIp, serverPort);
	}

	public void exit() {
		this.m.exit();
	}

	public void solve() throws IOException, InterruptedException {
		this.m.solve();
	}

	public void disconnect() {
		this.m.disconnect();
	}
}

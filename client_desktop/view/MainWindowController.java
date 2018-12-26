package view;


import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.FileChooser;
import view.dialogs.NakedMessage;
import view.dialogs.NakedObjectDisplayer;
import view.dialogs.ServerConfiguration;
import view.dialogs.ThemeConfiguration;
import viewModel.PipeGameViewModel;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.media.AudioClip.INDEFINITE;


public class MainWindowController implements Initializable {

	PipeGameViewModel vm;

	ListProperty<char[]> board;
	BooleanProperty isGoal;
	ListProperty<Point> flowPoints;
	IntegerProperty numOfSteps;
	
	AudioClip media;
	
	@FXML
	PipeDisplayer pipeDisplayer;
	@FXML
	TextField stepsText;
	@FXML
	Label connectionStatus;

	NakedObjectDisplayer nakedObjectDisplayer = new NakedObjectDisplayer();
	ServerConfiguration serverConfiguration = new ServerConfiguration();
	ThemeConfiguration themeConfiguration = new ThemeConfiguration();

	String currentTheme;
	String backgroundMusic;

	public void setViewModel(PipeGameViewModel vm) {
		this.vm = vm;
		this.board = new SimpleListProperty<>();
		this.board.bind(this.vm.board);
		this.board.addListener((observableValue, s, t1) -> {
			pipeDisplayer.setPipeData(this.board.toArray(new char[this.board.size()][]));
		});
		this.isGoal = new SimpleBooleanProperty();
		this.isGoal.bind(this.vm.isGoal);
		this.isGoal.addListener((observableValue, s, t1) -> {
			if (isGoal.get() == true) {
				NakedMessage nm = new NakedMessage("You Won!");
				nm.addMessage("Number of steps: " + numOfSteps.get());
				// Reset number of steps after solve
				numOfSteps.set(0);
				nakedObjectDisplayer.display(nm);
			}
		});
		this.flowPoints = new SimpleListProperty<Point>();
		this.flowPoints.bind(this.vm.flowPoints);
		this.flowPoints.addListener((observableValue, s, t1) -> {
			pipeDisplayer.setFlowPoints(this.flowPoints);
		});

		// click event
		pipeDisplayer.addEventHandler(MouseEvent.MOUSE_CLICKED,
				(MouseEvent t) -> {
					double w = pipeDisplayer.getWidth() / board.get(0).length;
					double h = pipeDisplayer.getHeight() / board.size();
					int x = (int) (t.getX() / w);
					int y = (int) (t.getY() / h);
					vm.changePipe(x, y);
				}
		);

		this.numOfSteps = new SimpleIntegerProperty();
		this.numOfSteps.bindBidirectional(this.vm.numOfSteps);
		this.numOfSteps.addListener((observableValue, s, t1) -> {
			this.stepsText.setText(Integer.toString(numOfSteps.get()));
		});
		this.connectionStatus.setText("Server Status: Disconnected");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		changeTheme("mario");
		pipeDisplayer.loadImages();
	}

	public void connect() {
		System.out.println("Connecting to " + this.serverConfiguration.ServerIp + ":" + this.serverConfiguration.ServerPort);
		try {
			this.vm.connect(this.serverConfiguration.ServerIp, this.serverConfiguration.ServerPort);
			this.connectionStatus.setText("Server Status: Connected");
		} catch (IOException e) {
			this.connectionStatus.setText("Server Status: Couldn't connect to the server");
			e.printStackTrace();
		}
	}

	public void solve() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            	try {
            		System.out.println("Solving.");
            		Platform.runLater(()->connectionStatus.setText("Server Status: Connecting to " + serverConfiguration.ServerIp + ":" + serverConfiguration.ServerPort));
            		vm.connect(serverConfiguration.ServerIp, serverConfiguration.ServerPort);
        			vm.solve();
        			vm.disconnect();
        			// must to be run in the UI thread
        			Platform.runLater(()->connectionStatus.setText("Server Status: Disconnected"));
        		} catch (IOException e) {
        			Platform.runLater(()->connectionStatus.setText("Server Status: Couldn't connect to the server"));
        			e.printStackTrace();
        		}
				return null;
            }
        };
        new Thread(task).start();
		
	}

	public void exit() {
		System.out.println("Exiting..");
		System.exit(0);
	}

	public void openFile() {
		System.out.println("Open File.");
		FileChooser fc = new FileChooser();
		fc.setTitle("Open Pipe File");
		fc.setInitialDirectory(new File("./resources"));

		FileChooser.ExtensionFilter txtExtensionFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
		fc.getExtensionFilters().add(txtExtensionFilter);
		fc.setSelectedExtensionFilter(txtExtensionFilter);
		File choosen = fc.showOpenDialog(null);

		if (choosen != null) {
			System.out.println(choosen.getName());
			vm.loadGame(choosen.getAbsolutePath());
		}
	}

	public void saveFile() {
		System.out.println("Saving into File.");
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose location To Save File");
		FileChooser.ExtensionFilter txtExtensionFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
		fc.getExtensionFilters().add(txtExtensionFilter);
		fc.setSelectedExtensionFilter(txtExtensionFilter);

		File selectedFile = fc.showSaveDialog(null);

		if (selectedFile == null) {
			return;
		}
		vm.saveGame(selectedFile);
	}

	public void serverConfig() {
		System.out.println("Server Config Dialog");
		nakedObjectDisplayer.display(this.serverConfiguration);
	}

	public void themeConfig() {
		ComboBox<String> comboBox = nakedObjectDisplayer.display(this.themeConfiguration);
		comboBox.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends String> observable,
						String oldValue, String newValue) -> changeTheme(newValue));
	}

	void changeTheme(String themeName) {
		if (themeName.equals(this.currentTheme)) {
			System.out.println("Same theme, not need to change.");
			return;
		}
		System.out.println("Changing theme to: " + themeName);
		pipeDisplayer.setBackgroundFileName("./resources/" + themeName + "/background.png");
		pipeDisplayer.setStartFileName("./resources/" + themeName + "/start.png");
		pipeDisplayer.setGoalFileName("./resources/" + themeName + "/goal.png");
		pipeDisplayer.setAnglePipeFileName("./resources/" + themeName + "/pipe_angle.png");
		pipeDisplayer.setVerticalPipeFileName("./resources/" + themeName + "/pipe_vertical.png");
		this.currentTheme = themeName;
		this.backgroundMusic = "./resources/" + themeName + "/music.mp3";
		pipeDisplayer.loadImages();
		pipeDisplayer.redraw();
		this.playMusic();
	}

	void playMusic() {
		if (null != this.media) {
			media.stop();
		}
		this.media = new AudioClip(new File(this.backgroundMusic).toURI().toString());
		media.setCycleCount(INDEFINITE);
		media.play();
	}
}
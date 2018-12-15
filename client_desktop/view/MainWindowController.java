package view;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import viewModel.PipeGameViewModel;

import java.awt.Point;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

	PipeGameViewModel vm;

	ListProperty<char[]> board;
	BooleanProperty isGoal;
	ListProperty<Point> flowPoints;

	@FXML
	PipeDisplayer pipeDisplayer;
	@FXML
	TextField stepsText;

	public void setViewModel(PipeGameViewModel vm) {
		this.vm = vm;
		stepsText.setText("0");
		this.board = new SimpleListProperty<char[]>();
		this.board.bindBidirectional(vm.board);
		this.board.addListener((observableValue, s, t1) -> {
			pipeDisplayer.setPipeData(this.board.toArray(new char[this.board.size()][]));
		});
		this.isGoal = new SimpleBooleanProperty();
		this.isGoal.bindBidirectional(this.vm.isGoal);
		this.isGoal.addListener((observableValue, s, t1) -> {
			if (isGoal.get() == true) {
				System.out.println("Win");
				final Stage dialog = new Stage();
		        dialog.initModality(Modality.APPLICATION_MODAL);
		        VBox dialogVbox = new VBox(20);
		        dialogVbox.getChildren().add(new Text("You Win!"));
		        Scene dialogScene = new Scene(dialogVbox, 50, 50);
		        dialog.setScene(dialogScene);
		        dialog.show();
			}
		});
		this.flowPoints = new SimpleListProperty<Point>();
		this.flowPoints.bindBidirectional(this.vm.flowPoints);
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
					stepsText.setText(Integer.toString(Integer.parseInt(stepsText.getText())+1));
				}
		);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pipeDisplayer.loadImages();
	}

	public void start() {
		System.out.println("Start.");
	}

	public void stop() {
		System.out.println("Stop.");
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
}
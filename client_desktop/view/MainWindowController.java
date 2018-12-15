package view;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import viewModel.PipeGameViewModel;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

	PipeGameViewModel vm;

	StringProperty board;
	ListProperty<char[]> boardList;

	@FXML
	PipeDisplayer pipeDisplayer;

	public void setViewModel(PipeGameViewModel vm) {
		this.vm = vm;
		
		this.boardList = new SimpleListProperty<char[]>();
		this.boardList.bindBidirectional(vm.boardList);
		this.boardList.addListener((observableValue, s, t1) -> {
			pipeDisplayer.setPipeData(this.boardList.toArray(new char[this.boardList.size()][]));
		});

		// click event
		pipeDisplayer.addEventHandler(MouseEvent.MOUSE_CLICKED,
				(MouseEvent t) -> {
					double w = pipeDisplayer.getWidth() / boardList.get(0).length;
					double h = pipeDisplayer.getHeight() / boardList.size();
					int x = (int) (t.getX() / w);
					int y = (int) (t.getY() / h);
					vm.changePipe(x, y);
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
package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import viewModel.PipeGameViewModel;

import java.io.*;
import java.net.URL;
import java.util.*;

public class MainWindowController implements Initializable, Observer {

	PipeGameViewModel vm;

	char[][] pipeData = {
			{'s', '-', '-', '-', '7', 'J', 'L', 'F', '7'},
			{'7', '7', '7', '7', '7', '7', '7', '7', '7'},
			{'7', '7', '7', ' ', ' ', ' ', '7', '7', '7'},
			{'7', '7', '7', '7', '|', '7', '7', '7', '7'},
			{'7', '7', '7', '7', 'L', '7', '7', '7', '7'},
			{'7', '7', '7', '7', '7', '7', '7', '7', '7'},
			{'7', '7', '7', '7', '|', '7', '7', '7', '7'},
			{'7', '7', '-', '-', '-', '-', '-', '-', 'g'},
	};

	@FXML
	PipeDisplayer pipeDisplayer;

	public void setViewModel(PipeGameViewModel vm) {
		this.vm = vm;
		return;
//		vm.x.bind(varX.textProperty()); 
//		vm.y.bind(varY.textProperty()); 
//		resultLabel.textProperty().bind(vm.result.asString());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pipeDisplayer.setPipeData(pipeData);
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

			List<char[]> lines = new ArrayList<char[]>();
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(choosen));

				String line;
				while ((line = reader.readLine()) != null) {
					lines.add(line.toCharArray());
				}
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			char[][] charArray = lines.toArray(new char[lines.size()][]);
			pipeDisplayer.setPipeData(charArray);
		}
	}

	public void saveFile() {
		System.out.println("Saving into File.");
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose location To Save File");
		FileChooser.ExtensionFilter txtExtensionFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
		fc.getExtensionFilters().add(txtExtensionFilter);
		fc.setSelectedExtensionFilter(txtExtensionFilter);

		File selectedFile = null;
		selectedFile = fc.showSaveDialog(null);

		if (selectedFile == null) {
			return;
		}

		PrintWriter outFile = null;
		try {
			outFile = new PrintWriter(selectedFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < pipeData.length; i++) {
			for (int j = 0; j < pipeData[i].length; j++) {
				outFile.print(pipeData[i][j]);
			}
			outFile.println();
		}

		outFile.close();
	}

	@Override
	public void update(Observable observable, Object obj) {
		// TODO Auto-generated method stub

	}
}

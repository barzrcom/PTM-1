package view;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

public class MainWindowController implements Initializable {
	
	int [][] pipeData = {
			{1,1,1,1,1,1,1,1,1},
			{0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,1,0,1},
			{1,0,1,1,1,0,1,0,1},
			{1,0,1,0,1,0,0,0,1},
			{1,0,0,0,1,0,1,0,1},
			{1,1,0,1,1,1,1,1,1}
	};
	
	@FXML
	PipeDisplayer pipeDisplayer;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pipeDisplayer.setPipeData(pipeData);
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
		
		FileChooser.ExtensionFilter xmlExtensionFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
	    fc.getExtensionFilters().add(xmlExtensionFilter);
	    fc.setSelectedExtensionFilter(xmlExtensionFilter);
		File choosen = fc.showOpenDialog(null);
		
		if (choosen != null) {
			System.out.println(choosen.getName());
		}
		
	}
}

package client;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.PipeGameModel;
import view.MainWindowController;
import viewModel.PipeGameViewModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class UiClient extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			
			PipeGameModel m = new PipeGameModel(); // The Model

			PipeGameViewModel vm = new PipeGameViewModel(m);  // The View-Model

			FXMLLoader fxl=new FXMLLoader();

			BorderPane root = fxl.load(getClass().getResource("../view/MainWindow.fxml").openStream());

			MainWindowController mwc = fxl.getController();
			mwc.setViewModel(vm);

			// must be placed after M-V-VM are bind together
			m.setInitializedBoard();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

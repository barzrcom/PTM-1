package view.dialogs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NakedObjectDisplayer {
	
	VBox dialogVbox;
	public void display(NakedObject obj) {
		
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);

		Field[] fields = obj.getClass().getFields();
		this.dialogVbox = new VBox(fields.length * 10);
		dialogVbox.setPadding(new Insets(10, 10, 10, 10));
		
		List<StringProperty> textPropertyList = new ArrayList<>();
		for (Field field: fields) {
			try {
				textPropertyList.add(createDisplayForField(field.getName(), (String)field.get(obj)));
			} catch (IllegalArgumentException | IllegalAccessException e) {

			}
		}
	    Button saveButton = new Button();
	    saveButton.setText("Apply");
	    saveButton.setStyle("-fx-font: 16 arial;");
	    saveButton.setOnAction(value ->  {
	    	for (int i=0; i< fields.length; i++) {
	    		try {
					fields[i].set(obj, textPropertyList.get(i).get());
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}
	    	dialog.close();
        });

	    this.dialogVbox.getChildren().add(saveButton);
	    
		
		Scene dialogScene = new Scene(dialogVbox);
		dialog.setScene(dialogScene);
		dialog.setAlwaysOnTop(true);
		dialog.setResizable(false);
		dialog.show();
	}
	
	private StringProperty createDisplayForField(String fieldName, String fieldValue) {
		//create UI element and bind to it using the NakedObject methods - this I'm leaving to you :)
		//This should obviously involve JavaFX code.
		Text caption = new Text(fieldName);
		caption.setStyle("-fx-font: 16 arial;");
		this.dialogVbox.getChildren().add(caption);
		TextField textBox = new TextField(fieldValue);
		textBox.setStyle("-fx-font: 16 arial;");
		this.dialogVbox.getChildren().add(textBox);
		return textBox.textProperty();
	}
	
	public void display(NakedMessage obj) {
		
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);

		this.dialogVbox = new VBox(obj.getMessageList().size() * 10);
		dialogVbox.setPadding(new Insets(10, 10, 10, 10));
		
		for (String message : obj.getMessageList()) {
			Text caption = new Text(message);
			caption.setStyle("-fx-font: 16 arial;");
			this.dialogVbox.getChildren().add(caption);
		}

	    Button saveButton = new Button();
	    saveButton.setText("OK");
	    saveButton.setStyle("-fx-font: 16 arial;");
	    saveButton.setOnAction(value ->  {
	    	dialog.close();
        });

	    this.dialogVbox.getChildren().add(saveButton);
	    
		Scene dialogScene = new Scene(dialogVbox);
		dialog.setScene(dialogScene);
		dialog.setAlwaysOnTop(true);
		dialog.setResizable(false);
		dialog.show();
		
		
	}
}

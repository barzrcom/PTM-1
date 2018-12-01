package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PipeDisplayer extends Canvas {

	char [][] pipeData;
	private StringProperty startFileName;
	private StringProperty goalFileName;
	private StringProperty pipe1FileName;
	private StringProperty pipe2FileName;
	private StringProperty pipe3FileName;
	private StringProperty pipe4FileName;
	private StringProperty pipe5FileName;
	private StringProperty pipe6FileName;
	
	public PipeDisplayer() {
		this.startFileName = new SimpleStringProperty();
		this.goalFileName = new SimpleStringProperty();
		this.pipe1FileName = new SimpleStringProperty();
		this.pipe2FileName = new SimpleStringProperty();
		this.pipe3FileName = new SimpleStringProperty();
		this.pipe4FileName = new SimpleStringProperty();
		this.pipe5FileName = new SimpleStringProperty();
		this.pipe6FileName = new SimpleStringProperty();
	}

	public String getStartFileName() {
		return startFileName.getValue();
	}

	public void setStartFileName(String startFileName) {
		this.startFileName.setValue(startFileName);;
	}

	public String getGoalFileName() {
		return goalFileName.getValue();
	}

	public void setGoalFileName(String goalFileName) {
		this.goalFileName.setValue(goalFileName);;
	}

	public String getPipe1FileName() {
		return pipe1FileName.getValue();
	}

	public void setPipe1FileName(String pipe1FileName) {
		this.pipe1FileName.setValue(pipe1FileName);;
	}

	public String getPipe2FileName() {
		return pipe2FileName.getValue();
	}

	public void setPipe2FileName(String pipe2FileName) {
		this.pipe2FileName.setValue(pipe2FileName);;
	}

	public String getPipe3FileName() {
		return pipe3FileName.getValue();
	}

	public void setPipe3FileName(String pipe3FileName) {
		this.pipe3FileName.setValue(pipe3FileName);;
	}

	public String getPipe4FileName() {
		return pipe4FileName.getValue();
	}

	public void setPipe4FileName(String pipe4FileName) {
		this.pipe4FileName.setValue(pipe4FileName);;
	}

	public String getPipe5FileName() {
		return pipe5FileName.getValue();
	}

	public void setPipe5FileName(String pipe5FileName) {
		this.pipe5FileName.setValue(pipe5FileName);;
	}

	public String getPipe6FileName() {
		return pipe6FileName.getValue();
	}

	public void setPipe6FileName(String pipe6FileName) {
		this.pipe6FileName.setValue(pipe6FileName);;
	}

	public void setPipeData(char [][] pipeData) {
		this.pipeData = pipeData;
		this.cleanGame();
		this.redraw();
	}

	public void cleanGame() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
	}

	public void redraw() {
		if (pipeData != null) {
			double W = getWidth();
			double H = getHeight();
			double w = W / pipeData[0].length;
			double h = H / pipeData.length;
			
			GraphicsContext gc = getGraphicsContext2D();
			
			for (int i = 0; i < pipeData.length; i++) {
				for (int j = 0; j < pipeData[i].length; j++) {
					String fileName;
					switch (pipeData[i][j]) {
					case 'L':
						fileName = pipe3FileName.get();
						break;
					case 'F':
						fileName = pipe4FileName.get();
						break;
					case '7':
						fileName = pipe5FileName.get();
						break;
					case 'J':
						fileName = pipe6FileName.get();
						break;
					case '-':
						fileName = pipe2FileName.get();
						break;
					case '|':
						fileName = pipe1FileName.get();
						break;
					case 's':
						fileName = startFileName.get();
						break;
					case 'g':
						fileName = goalFileName.get();
						break;
					case ' ':
						fileName = null;
						break;
					default:
						fileName = null;
						break;
					}

					if (fileName != null) {
						try {
							gc.drawImage(new Image(new FileInputStream(fileName)), j*w, i*h, w, h);
						} catch (FileNotFoundException e) {
							gc.fillRect(j*w, i*h, w, h);
						}
					}
				}
			}
		}
	}
}

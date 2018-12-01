package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class PipeDisplayer extends Canvas {

	char [][] pipeData;
	private StringProperty startFileName;
	private StringProperty goalFileName;
	private StringProperty anglePipeFileName;
	private StringProperty verticalPipeFileName;
	
	public PipeDisplayer() {
		this.startFileName = new SimpleStringProperty();
		this.goalFileName = new SimpleStringProperty();
		this.anglePipeFileName = new SimpleStringProperty();
		this.verticalPipeFileName = new SimpleStringProperty();
		
		//this.widthProperty().addListener(observable -> redraw());
		//this.heightProperty().addListener(observable -> redraw());
	}
	
	public boolean isResizable()
	{
		return true;
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

	public String getAnglePipeFileName() {
		return anglePipeFileName.getValue();
	}

	public void setAnglePipeFileName(String anglePipeFileName) {
		this.anglePipeFileName.setValue(anglePipeFileName);;
	}

	public String getVerticalPipeFileName() {
		return verticalPipeFileName.getValue();
	}

	public void setVerticalPipeFileName(String verticalPipeFileName) {
		this.verticalPipeFileName.setValue(verticalPipeFileName);;
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
			int rotate = 0;
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			
			for (int i = 0; i < pipeData.length; i++) {
				for (int j = 0; j < pipeData[i].length; j++) {
					String fileName;
					switch (pipeData[i][j]) {
					case 'L':
						rotate = 0;
						fileName = anglePipeFileName.get();
						break;
					case 'F':
						rotate = 90;
						fileName = anglePipeFileName.get();
						break;
					case '7':
						rotate = 180;
						fileName = anglePipeFileName.get();
						break;
					case 'J':
						rotate = 270;
						fileName = anglePipeFileName.get();
						break;
					case '-':
						rotate = 90;
						fileName = verticalPipeFileName.get();
						break;
					case '|':
						rotate = 0;
						fileName = verticalPipeFileName.get();
						break;
					case 's':
						rotate = 0;
						fileName = startFileName.get();
						break;
					case 'g':
						rotate = 0;
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
							ImageView iv = new ImageView(new Image(new FileInputStream(fileName)));
							iv.setRotate(rotate);
							Image rotatedImage = iv.snapshot(params, null);
							gc.drawImage(rotatedImage, j*w, i*h, w, h);
						} catch (FileNotFoundException e) {
							gc.fillRect(j*w, i*h, w, h);
						}
						
					}
				}
			}
		}
	}
}

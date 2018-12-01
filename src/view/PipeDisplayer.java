package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PipeDisplayer extends Canvas {

	int [][] pipeData;
	private StringProperty wallFileName;
	int cCol, cRow;
	
	public PipeDisplayer() {
		this.wallFileName = new SimpleStringProperty();
		cCol = 0;
		cRow = 1;
	}
	
	public String getWallFileName() {
		return wallFileName.getValue();
	}

	public void setWallFileName(String wallFileName) {
		this.wallFileName.setValue(wallFileName);;
	}

	public void setPipeData(int [][] pipeData) {
		this.pipeData = pipeData;
		this.redraw();
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
					if (pipeData[i][j] != 0) {
						try {
							gc.drawImage(new Image(new FileInputStream(wallFileName.get())), j*w, i*h, w, h);
						} catch (FileNotFoundException e) {
							gc.fillRect(j*w, i*h, w, h);
						}
					}
				}
			}
			
		}
	}
}

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
	private StringProperty backgroundFileName;
	private StringProperty startFileName;
	private StringProperty goalFileName;
	private StringProperty anglePipeFileName;
	private StringProperty verticalPipeFileName;
	
	
	private Image background;
	private Image startImage;
	private Image goalImage;
	private Image pipeVImage;
	private Image pipeAImage;
	
	public PipeDisplayer() {
		this.backgroundFileName = new SimpleStringProperty();
		this.startFileName = new SimpleStringProperty();
		this.goalFileName = new SimpleStringProperty();
		this.anglePipeFileName = new SimpleStringProperty();
		this.verticalPipeFileName = new SimpleStringProperty();
	}
	public String getBackgroundFileName() {
		return backgroundFileName.getValue();
	}

	public void setBackgroundFileName(String backgroundFileName) {
		this.backgroundFileName.setValue(backgroundFileName);
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
		this.goalFileName.setValue(goalFileName);
	}

	public String getAnglePipeFileName() {
		return anglePipeFileName.getValue();
	}

	public void setAnglePipeFileName(String anglePipeFileName) {
		this.anglePipeFileName.setValue(anglePipeFileName);
	}

	public String getVerticalPipeFileName() {
		return verticalPipeFileName.getValue();
	}

	public void setVerticalPipeFileName(String verticalPipeFileName) {
		this.verticalPipeFileName.setValue(verticalPipeFileName);
	}

	public void setPipeData(char [][] pipeData) {
		this.pipeData = pipeData;
		this.redraw();
	}
	@Override
	public boolean isResizable() {
	    return true;
	}

    @Override
    public double minHeight(double width)
    {
        return 64;
    }

    @Override
    public double maxHeight(double width)
    {
        return 1000;
    }

    @Override
    public double prefHeight(double width)
    {
        return minHeight(width);
    }

    @Override
    public double minWidth(double height)
    {
        return 0;
    }

    @Override
    public double maxWidth(double height)
    {
        return 10000;
    }
	
    @Override
    public void resize(double width, double height)
    {
        super.setWidth(width);
        super.setHeight(height);
        this.redraw();
    }
	public void cleanGame() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
	}
	public void loadImages() {
		try {
			background = new Image(new FileInputStream(backgroundFileName.get()));
			startImage = new Image(new FileInputStream(startFileName.get()));
			goalImage = new Image(new FileInputStream(goalFileName.get()));
			pipeVImage = new Image(new FileInputStream(verticalPipeFileName.get()));
			pipeAImage = new Image(new FileInputStream(anglePipeFileName.get()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void redraw() {
		if (pipeData != null) {
			cleanGame();
			double W = getWidth();
			double H = getHeight();
			double w = W / pipeData[0].length;
			double h = H / pipeData.length;
			
			GraphicsContext gc = getGraphicsContext2D();
			gc.drawImage(background, 0, 0, getWidth(), getHeight());
			
			int rotate = 0;
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			
			for (int i = 0; i < pipeData.length; i++) {
				for (int j = 0; j < pipeData[i].length; j++) {
					Image fileName;
					switch (pipeData[i][j]) {
					case 'L':
						rotate = 0;
						fileName = pipeAImage;
						break;
					case 'F':
						rotate = 90;
						fileName = pipeAImage;
						break;
					case '7':
						rotate = 180;
						fileName = pipeAImage;
						break;
					case 'J':
						rotate = 270;
						fileName = pipeAImage;
						break;
					case '-':
						rotate = 90;
						fileName = pipeVImage;
						break;
					case '|':
						rotate = 0;
						fileName = pipeVImage;
						break;
					case 's':
						rotate = 0;
						fileName = startImage;
						break;
					case 'g':
						rotate = 0;
						fileName = goalImage;
						break;
					case ' ':
						fileName = null;
						break;
					default:
						fileName = null;
						break;
					}

					if (fileName != null) {
						ImageView iv = new ImageView(fileName);
						iv.setRotate(rotate);
						Image rotatedImage = iv.snapshot(params, null);
						gc.drawImage(rotatedImage, j*w, i*h, w, h);
					}
				}
			}
		}
	}
}

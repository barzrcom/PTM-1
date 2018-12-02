package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import board.PipeGameBoard;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	private Image pipeVerticalImage;
	private Image pipeHorizontalImage;
	private Image pipeAngle0Image;
	private Image pipeAngle90Image;
	private Image pipeAngle180Image;
	private Image pipeAngle270Image;
	
	public PipeDisplayer() {
		this.backgroundFileName = new SimpleStringProperty();
		this.startFileName = new SimpleStringProperty();
		this.goalFileName = new SimpleStringProperty();
		this.anglePipeFileName = new SimpleStringProperty();
		this.verticalPipeFileName = new SimpleStringProperty();
		// click event
		addEventHandler(MouseEvent.MOUSE_CLICKED, 
	        new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent t) {  
	    			double W = getWidth();
	    			double H = getHeight();
	    			double w = W / pipeData[0].length;
	    			double h = H / pipeData.length;
	    			int x = (int)(t.getX() / w);
	    			int y = (int)(t.getY() /h);
	                System.out.println((int)(t.getX() / w) + "," + (int)(t.getY() /h));
	                pipeData[y][x] = PipeGameBoard.changePipe(pipeData[y][x], 1);
	                redraw();
	            }
	        });
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
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			background = new Image(new FileInputStream(backgroundFileName.get()));
			startImage = new Image(new FileInputStream(startFileName.get()));
			goalImage = new Image(new FileInputStream(goalFileName.get()));
			pipeVerticalImage = new Image(new FileInputStream(verticalPipeFileName.get()));
			
			ImageView iv = new ImageView(pipeVerticalImage);
			iv.setRotate(90);
			pipeHorizontalImage = iv.snapshot(params, null);
			
			pipeAngle0Image = new Image(new FileInputStream(anglePipeFileName.get()));
			iv = new ImageView(pipeAngle0Image);
			iv.setRotate(90);
			pipeAngle90Image = iv.snapshot(params, null);
			iv.setRotate(180);
			pipeAngle180Image = iv.snapshot(params, null);
			iv.setRotate(270);
			pipeAngle270Image = iv.snapshot(params, null);
			
			
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

			for (int i = 0; i < pipeData.length; i++) {
				for (int j = 0; j < pipeData[i].length; j++) {
					Image pipeImage;
					switch (pipeData[i][j]) {
					case 'L':
						pipeImage = pipeAngle0Image;
						break;
					case 'F':
						pipeImage = pipeAngle90Image;
						break;
					case '7':
						pipeImage = pipeAngle180Image;
						break;
					case 'J':
						pipeImage = pipeAngle270Image;
						break;
					case '-':
						pipeImage = pipeHorizontalImage;
						break;
					case '|':
						pipeImage = pipeVerticalImage;
						break;
					case 's':
						pipeImage = startImage;
						break;
					case 'g':
						pipeImage = goalImage;
						break;
					case ' ':
						pipeImage = null;
						break;
					default:
						pipeImage = null;
						break;
					}

					if (pipeImage != null) {
						gc.drawImage(pipeImage, j*w, i*h, w, h);
					}
				}
			}
		}
	}
}

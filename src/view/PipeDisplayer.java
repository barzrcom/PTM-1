package view;

import board.PipeGameBoard;
import board.PipeGameBoard.directions;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;

public class PipeDisplayer extends Canvas {

	char[][] pipeData;
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

	private Point startIndex;
	private LinkedHashSet<Point> flowPointList = new LinkedHashSet<Point>();

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
						double w = getWidth() / pipeData[0].length;
						double h = getHeight() / pipeData.length;
						int x = (int) (t.getX() / w);
						int y = (int) (t.getY() / h);
						//System.out.println((int)(t.getX() / w) + "," + (int)(t.getY() /h));
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
		this.startFileName.setValue(startFileName);
		;
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

	public void setPipeData(char[][] pipeData) {
		this.pipeData = pipeData;
		for (int i = 0; i < pipeData.length; i++) {
			for (int j = 0; j < pipeData[i].length; j++) {
				if (pipeData[i][j] == 's') {
					startIndex = new Point(j, i);
					break;
				}
			}
		}
		this.redraw();
	}

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double minHeight(double width) {
		return 64;
	}

	@Override
	public double maxHeight(double width) {
		return 1000;
	}

	@Override
	public double prefHeight(double width) {
		return minHeight(width);
	}

	@Override
	public double minWidth(double height) {
		return 0;
	}

	@Override
	public double maxWidth(double height) {
		return 10000;
	}

	@Override
	public void resize(double width, double height) {
		super.setWidth(width);
		super.setHeight(height);
		this.redraw();
	}

	public void cleanGame() {
		flowPointList.clear();
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
			isGoalStateLogic(startIndex.x, startIndex.y, directions.start);
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
						if (flowPointList.contains(new Point(j, i))) {
							gc.save();
							Bloom bloom = new Bloom();
							bloom.setThreshold(0.5);
							gc.setEffect(bloom);
							gc.drawImage(pipeImage, j * w, i * h, w, h);
							gc.restore();
						} else {
							gc.drawImage(pipeImage, j * w, i * h, w, h);
						}
					}
				}
			}
		}
	}

	private boolean isGoalStateLogic(int posX, int posY, directions from) {
		//check bounds
		if (posX < 0 || posX >= pipeData[0].length)
			return false;
		if (posY < 0 || posY >= pipeData.length)
			return false;
		// start
		if (from == directions.start) {
			return (isGoalStateLogic(posX + 1, posY, directions.left) ||
					isGoalStateLogic(posX - 1, posY, directions.right) ||
					isGoalStateLogic(posX, posY + 1, directions.up) ||
					isGoalStateLogic(posX, posY - 1, directions.down));
		}

		switch (pipeData[posY][posX]) {
			case 'g':
				flowPointList.add(new Point(posX, posY));
				return true;
			case '-':
				if (from == directions.left) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX + 1, posY, directions.left);
				} else if (from == directions.right) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX - 1, posY, directions.right);
				} else {
					return false;
				}
			case '|':
				if (from == directions.up) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY + 1, directions.up);
				} else if (from == directions.down) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY - 1, directions.down);
				} else {
					return false;
				}
			case 'L':
				if (from == directions.up) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX + 1, posY, directions.left);
				} else if (from == directions.right) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY - 1, directions.down);
				} else {
					return false;
				}
			case 'F':
				if (from == directions.right) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY + 1, directions.up);
				} else if (from == directions.down) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX + 1, posY, directions.left);
				} else {
					return false;
				}
			case '7':
				if (from == directions.left) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY + 1, directions.up);
				} else if (from == directions.down) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX - 1, posY, directions.right);
				} else {
					return false;
				}
			case 'J':
				if (from == directions.up) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX - 1, posY, directions.right);
				} else if (from == directions.left) {
					flowPointList.add(new Point(posX, posY));
					return isGoalStateLogic(posX, posY - 1, directions.down);
				} else {
					return false;
				}
		}
		return false;
	}
}

package board;

import java.awt.Point;

public class PipeStep extends Step {
	public int x;
	public int y;
	
	public PipeStep(Point pt) {
		this.x = pt.x;
		this.y = pt.y;
	}
	public PipeStep(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String ToString() {
		return this.y + "," + this.x + "," + 1;
	}

}

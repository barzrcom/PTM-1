package board;

import java.awt.Point;

public class PipeStep extends Step {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7266968501601282342L;
	public int x;
	public int y;
	
	public PipeStep(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public PipeStep(Point pt) {
		this.x = pt.x;
		this.y = pt.y;
	}

	@Override
	public String ToString() {
		return this.y + "," + this.x + "," + 1;
	}

}

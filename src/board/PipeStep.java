package board;

import java.awt.Point;

public class PipeStep extends Step {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7266968501601282342L;
	public int x;
	public int y;
	
	public PipeStep(int x, int y, int cost) {
		this.x = x;
		this.y = y;
		this.setCost(cost);
	}
	public PipeStep(Point pt, int cost) {
		this.x = pt.x;
		this.y = pt.y;
		this.setCost(cost);
	}

	@Override
	public String ToString() {
		return this.y + "," + this.x + "," + cost;
	}

	@Override
	public int hashCode() {
		return ToString().hashCode();
	}
}

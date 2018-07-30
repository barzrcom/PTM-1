package board;

import java.io.Serializable;

public abstract class Step implements Serializable{

    /**
	 * 
	 */
	protected int cost;
	private static final long serialVersionUID = 43L;
	@Override
    public String toString()
    {
        return ToString();
    }
    public abstract String ToString();
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
}

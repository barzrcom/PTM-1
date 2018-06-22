package board;
import java.awt.Point;

public class State implements Comparable<State>{
	final private char[][] state;
	private double cost = 0;
	private State cameFrom;
	private Point posClicked;
	final private int hash;
	
	public State(char[][] state) {
		this.state = state;
		hash = java.util.Arrays.deepHashCode(state);
	}

	public State(char[][] state, Point posClicked) {
		this.state = state;
		this.hash = java.util.Arrays.deepHashCode(state);
		this.posClicked = posClicked;
	}
	
	@Override
	public boolean equals(Object s) {
		return (this.hashCode() == s.hashCode());
	}

	public State getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State cameFrom) {
		this.cameFrom = cameFrom;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public char[][] getState() {
		return state;
	}
	
	public void printState() {
		for (int i=0; i < this.state.length; i++) {
			System.out.println(this.state[i]);
		}
	}
	
	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public int compareTo(State o) {
		// TODO Auto-generated method stub
		return Double.compare(this.cost, o.cost);
	}
	public Point getPosClicked() {
		return posClicked;
	}
	public void setPosClicked(Point posClicked) {
		this.posClicked = posClicked;
	}
}

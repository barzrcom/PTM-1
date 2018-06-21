package board;

import java.util.Arrays;

public class State implements Comparable<State>{
	private char[][] state;
	private double cost;
	private State cameFrom;
	
	public State(char[][] state) {
		this.state = state;
		this.cost = 0;
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
		String s = "";

		for (int i=0; i < this.state.length; i++) {
			for (int j=0; j < this.state[i].length; j++) {
				s += this.state[i][j];
			}
		}

		return s.hashCode();
	}

	@Override
	public int compareTo(State o) {
		// TODO Auto-generated method stub
		return Double.compare(this.cost, o.cost);
	}
}

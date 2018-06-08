package server;

import java.util.Arrays;

public class State {
	private char[][] state;
	private double cost;
	private char[][] cameFrom;
	
	public State(char[][] state) {
		this.state = state;
	}

	public boolean equals(State s) {
		return Arrays.equals(this.state, s.state);
	}

	public char[][] getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(char[][] cameFrom) {
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
}

package board;
import java.awt.Point;

public class State implements Comparable<State>{
	final private char[][] state;
	private double cost = 0;
	private int grade = Integer.MAX_VALUE;
	private State cameFrom;
	private Step step;
	private int hash;
	
	
	public State(char[][] state) {
		hash = java.util.Arrays.deepHashCode(state);
		this.state = state;
	}

	public State(char[][] state, Step step) {
		hash = java.util.Arrays.deepHashCode(state);
		this.state = state;
		this.step = step;
	}
	
	@Override
	public boolean equals(Object s) {
		return (this.hashCode() == s.hashCode());
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
	
	public Step getStep() {
		return step;
	}
	public void setStep(Step step) {
		this.step = step;
	}
	
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
}

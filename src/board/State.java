package board;

import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedHashSet;

public class State<T> implements Comparable<State<T>>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 42L;
	private State<?> cameFrom;
	private double cost = 0;
	private double grade = Double.MAX_VALUE;
	private int hash;
	public LinkedHashSet<Point> neighborsPointList = new LinkedHashSet<Point>();
	final private T state;
	private Step step;
	
	
	public State(T state) {
		hash = state.hashCode();
		this.state = state;
	}

	public State(T state, Step step) {
		hash = state.hashCode();
		this.state = state;
		this.step = step;
	}
	
	@Override
	public int compareTo(State<T> o) {
		// TODO Auto-generated method stub
		return Double.compare(this.cost, o.cost);
	}
	
	@Override
	public boolean equals(Object s) {
		return (this.hashCode() == s.hashCode());
	}

	public State<?> getCameFrom() {
		return cameFrom;
	}

	public double getCost() {
		return cost;
	}

	public double getGrade() {
		return grade;
	}

	public T getState() {
		return state;
	}

	public Step getStep() {
		return step;
	}
	
	@Override
	public int hashCode() {
		return hash;
	}
	
	public void setCameFrom(State<?> cameFrom) {
		this.cameFrom = cameFrom;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public void setStep(Step step) {
		this.step = step;
	}
}

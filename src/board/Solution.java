package board;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Solution {
	List<Step> stepList = new LinkedList<Step>();
	State initialState;
	State goalState;
	
	public Solution(State initialState, State goalState) {
		this.initialState = initialState;
		this.goalState = goalState;
		
		State currentState = goalState;
		while(currentState.getCameFrom() != null) {
			// add step
			stepList.add(currentState.getStep());
			currentState = currentState.getCameFrom();
		}
		Collections.reverse(stepList);
	}
	public void addStep(Step step) {
		stepList.add(step);
	}
	public List<Step> getStepList() {
		return stepList;
	}
}

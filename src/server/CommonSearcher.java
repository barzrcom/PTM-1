package server;

import java.util.PriorityQueue;

public abstract class CommonSearcher implements Searcher {

	protected PriorityQueue<State> openList;
	private int evaluatedNodes;
	
	public CommonSearcher() {
		this.openList = new PriorityQueue<State>();
		this.evaluatedNodes=0;
	}
	
	protected void addToOpenList(State state) {
		this.openList.add(state);
	}
	
	protected State popOpenList() {
		this.evaluatedNodes++;
		return openList.poll();
	}
	
	public int getNumberOfNodesEvaluated() {
		return this.evaluatedNodes;
	}
	
	public boolean openListContains(State state) {
		return openList.contains(state);
	}
	
	public Solution backTrace(State goalState, State initialState) {
		// Solution
		return null;
	}

}

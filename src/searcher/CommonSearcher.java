package searcher;
import java.awt.Point;
import java.util.PriorityQueue;
import board.State;
import board.Solution;

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
		Solution solution = new Solution();
		State currentState = goalState;
		while(currentState.getCameFrom() != null) {
			// add step
			Point posClicked = currentState.getPosClicked();
			solution.addStep(posClicked.x + "," + 
							posClicked.y + "," +
							1);
			currentState = currentState.getCameFrom();
		}
		solution.reverse();
		return solution;
	}

}

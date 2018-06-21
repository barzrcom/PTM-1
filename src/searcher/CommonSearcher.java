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
	
	public abstract Solution algoSearch(Searchable s);
	
	@Override
	public Solution search(Searchable s) {
		long startTime = System.currentTimeMillis();
		System.out.println("algoSearch start time: " + startTime);
		
		Solution solution = algoSearch(s);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("algoSearch finished time: " + stopTime);
		double elapsedTime = stopTime - startTime;
		System.out.println("algoSearch total seconds: " + elapsedTime/1000);
		System.out.println("algoSearch total evaluated nodes: " + evaluatedNodes);
		return solution;
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

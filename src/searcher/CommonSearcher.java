package searcher;
import java.awt.Point;
import java.util.Queue;

import board.State;
import board.Step;
import board.Solution;

public abstract class CommonSearcher implements Searcher {

	protected Queue<State> openList;
	protected int evaluatedNodes = 0;
	
	public CommonSearcher(Queue<State> queue) {
		this.openList = queue;
	}
	
	public abstract Solution algoSearch(Searchable s);
	
	@Override
	public Solution search(Searchable s) {
		this.evaluatedNodes=0;
		long startTime = System.currentTimeMillis();
		System.out.println("algoSearch started");
		
		Solution solution = algoSearch(s);
		
		long stopTime = System.currentTimeMillis();
		double elapsedTime = stopTime - startTime;
		System.out.println("algoSearch total seconds: " + elapsedTime/1000);
		System.out.println("algoSearch total evaluated nodes: " + evaluatedNodes);
		return solution;
	}
	
	protected void addToOpenList(State state) {
		this.openList.add(state);
	}
	
	public void incEvaluatedNodes() {
		this.evaluatedNodes++;
	}
	
	protected State popOpenList() {
		incEvaluatedNodes();
		return openList.poll();
	}
	
	public int getNumberOfNodesEvaluated() {
		return this.evaluatedNodes;
	}
	
	public boolean openListContains(State state) {
		return openList.contains(state);
	}


}

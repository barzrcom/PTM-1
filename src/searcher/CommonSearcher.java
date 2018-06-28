package searcher;

import java.util.Queue;

import board.Solution;
import board.State;

public abstract class CommonSearcher implements Searcher {

	protected Queue<State<?>> openList;
	protected int evaluatedNodes = 0;
	
	public <T> CommonSearcher(Queue<State<?>> queue) {
		this.openList = queue;
	}
	
	public abstract <T> Solution algoSearch(Searchable<T> s);
	
	@Override
	public Solution search(Searchable<?> s) {
		long startTime = System.currentTimeMillis();
		System.out.println("algoSearch started");
		
		Solution solution = algoSearch(s);
		
		long stopTime = System.currentTimeMillis();
		double elapsedTime = stopTime - startTime;
		System.out.println("algoSearch total seconds: " + elapsedTime/1000);
		System.out.println("algoSearch total evaluated nodes: " + evaluatedNodes);
		return solution;
	}
	
	protected void addToOpenList(State<?> state) {
		this.openList.add(state);
	}
	
	public void incEvaluatedNodes() {
		this.evaluatedNodes++;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> State<T> popOpenList() {
		incEvaluatedNodes();
		return (State<T>) openList.poll();
	}
	
	public int getNumberOfNodesEvaluated() {
		return this.evaluatedNodes;
	}
	
	public boolean openListContains(State<?> state) {
		return openList.contains(state);
	}
	
	public void reset() {
		openList.clear();
		evaluatedNodes = 0;
	}


}

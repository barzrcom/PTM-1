package searcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import board.Solution;
import board.State;

public class DFSSearcher extends CommonSearcher {

	
	
	public DFSSearcher() {
		// we need a fast queue
		super(null);
	}
	
	@Override
	public <T> Solution algoSearch(Searchable<T> s) {
		Stack<State<T>> stack = new Stack<State<T>>();
		
		stack.add(s.getInitialState());
		Set<State<T>> closedSet = new HashSet<State<T>>();

		while (!stack.isEmpty()) {
			State<T> n = stack.pop(); 
			// manually count nodes
			incEvaluatedNodes();
			closedSet.add(n);
			
			if (s.isGoalState(n)) {
				return new Solution(s.getInitialState(), n);
			}

			List<State<T>> neighbors = s.getAllPossibleStates(n);

			for (State<T> neighbor : neighbors) {
				if (!closedSet.contains(neighbor) && !stack.contains(neighbor)) {
					neighbor.setCameFrom(n);
					neighbor.setCost(n.getCost() + 1); // update cost
					stack.add(neighbor);
				} else {
					// all edges are 1, no need to update the cost
				}
			}
		}
		return null;
	}


}

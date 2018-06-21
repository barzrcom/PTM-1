package searcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import board.Solution;
import board.State;

public class DFSSearcher extends CommonSearcher {

	Stack<State> stack = new Stack<State>();
	
	public DFSSearcher() {
		// we need a fast queue
		super(null);
	}
	
	@Override
	public Solution algoSearch(Searchable s) {
		stack.add(s.getInitialState());
		Set<State> closedSet = new HashSet<State>();

		while (!stack.isEmpty()) {
			State n = stack.pop(); 
			// manually count nodes
			incEvaluatedNodes();
			closedSet.add(n);
			
			if (s.isGoalState(n)) {
				n.printState();
				return backTrace(n, s.getInitialState());
			}

			List<State> neighbors = s.getAllPossibleStates(n);

			for (State neighbor : neighbors) {
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

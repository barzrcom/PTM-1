package searcher;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import board.Solution;
import board.State;

public class BFSSearcher extends CommonSearcher {

	public BFSSearcher() {
		// we need a fast queue
		super(new ArrayDeque<State<?>>());
	}

	@Override
	public <T> Solution algoSearch(Searchable<T> s) {
		addToOpenList(s.getInitialState());
		Set<State<?>> closedSet = new HashSet<State<?>>();

		while (!openList.isEmpty()) {
			State<T> n = popOpenList(); 
			closedSet.add(n);
			
			if (s.isGoalState(n)) {
				return new Solution(s.getInitialState(), n);
			}

			List<State<T>> neighbors = s.getAllPossibleStates(n);

			for (State<T> neighbor : neighbors) {
				if (!closedSet.contains(neighbor) && !openListContains(neighbor)) {
					neighbor.setCameFrom(n);
					neighbor.setCost(n.getCost() + 1); // update cost
					addToOpenList(neighbor);
				} else {
					// all edges are 1, no need to update the cost
				}
			}
		}
		return null;
	}


}

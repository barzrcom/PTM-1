package searcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import board.Solution;
import board.State;

public class BestFirstSearchSearcher extends CommonSearcher {

	@Override
	public Solution search(Searchable s) {
		addToOpenList(s.getInitialState());
		Set<State> closedSet = new HashSet<State>();
		
		while (!openList.isEmpty()) {
			State n = popOpenList(); 
			closedSet.add(n);
			
			if (s.isGoalState(n)) {
				n.printState();
				return backTrace(n, s.getInitialState());
			}

			List<State> neighbors = s.getAllPossibleStates(n);

			for (State neighbor : neighbors) {
				if (!closedSet.contains(neighbor) && ! openListContains(neighbor)) {
					neighbor.setCameFrom(n);
					neighbor.setCost(n.getCost() + 1); // update cost
					addToOpenList(neighbor);
				} else {
					
				}
			}
		}
		return null;
	}
}

package server;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BestFirstSearchSearcher extends CommonSearcher {

	@Override
	public Solution search(Searchable s) {
		addToOpenList(s.getInitialState());
		Set<State> closedSet = new HashSet<State>();
		
		while (!openList.isEmpty()) {
			State n = popOpenList(); 
			closedSet.add(n);
			
			if (s.isGoalState(n)) {
				return backTrace(n, s.getInitialState());
			}

			List<State> neighbors = s.getAllPossibleStates(n);

			for (State neighbor : neighbors) {
				System.out.println(closedSet.contains(neighbor));
				System.out.println(openListContains(neighbor));
				if (!closedSet.contains(neighbor) && ! openListContains(neighbor)) {
					neighbor.printState();
					System.out.println(neighbor.hashCode());
					neighbor.setCameFrom(n.getState());
					neighbor.setCost(n.getCost() + 1); // update cost
					addToOpenList(neighbor);
				} else {
					System.out.println("already exist");
				}
			}
		}
		return null;
	}
}

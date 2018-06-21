package searcher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import board.Solution;
import board.State;

public class AStarSearcher extends CommonSearcher {

	public AStarSearcher() {
		super(new PriorityQueue<State>());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Solution algoSearch(Searchable s) {
		addToOpenList(s.getInitialState());
		Set<State> closedSet = new HashSet<State>();
		
		while (!openList.isEmpty()) {
			State n = popOpenList(); 
			closedSet.add(n);
			
			if (s.isGoalState(n)) {
				n.printState();
				return backTrace(n);
			}

			List<State> neighbors = s.getAllPossibleStates(n);

			for (State neighbor : neighbors) {
				if (!closedSet.contains(neighbor)) {
					neighbor.setCameFrom(n);
					// remove old heuristic grade & add 1 step & add new heuristic
					neighbor.setCost(n.getCost() - s.grade(n) + 1 + s.grade(neighbor)); 
					if (!openListContains(neighbor)) {
						addToOpenList(neighbor);
					} else if (openList.removeIf(e -> e.equals(neighbor) && e.getCost() > neighbor.getCost())) {
						addToOpenList(neighbor);
					}
				}
			}
		}
		return null;
	}


}

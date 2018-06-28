package searcher;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import board.Solution;
import board.State;

public class BestFirstSearchSearcher extends CommonSearcher {

	public BestFirstSearchSearcher() {
		super(new PriorityQueue<State<?>>());
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> Solution algoSearch(Searchable<T> s) {
		addToOpenList(s.getInitialState());
		Set<State<T>> closedSet = new HashSet<State<T>>();
		
		while (!openList.isEmpty()) {
			State<T> n = popOpenList(); 
			closedSet.add(n);
			
			if (s.isGoalState(n)) {
				n.printState();
				return new Solution(s.getInitialState(), n);
			}

			List<State<T>> neighbors = s.getAllPossibleStates(n);

			for (State<T> neighbor : neighbors) {
				if (!closedSet.contains(neighbor)) {
					neighbor.setCameFrom(n);
					// remove old heuristic grade & add 1 step & add new heuristic
					neighbor.setCost(n.getCost() + 1); 
					if (!openListContains(neighbor)) {
						addToOpenList(neighbor);
					} 
//					else if (openList.removeIf(e -> e.equals(neighbor) && e.getCost() > neighbor.getCost())) {
//						addToOpenList(neighbor);
//					}
				}
			}
		}
		return null;
	}


}

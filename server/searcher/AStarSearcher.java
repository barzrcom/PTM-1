package searcher;

import board.Solution;
import board.State;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class AStarSearcher extends CommonSearcher {

	public AStarSearcher() {
		super(new PriorityQueue<State<?>>());
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> Solution algoSearch(Searchable<T> s) {
		State<T> state = s.getInitialState();
		state.setGrade(s.grade(state));
		addToOpenList(state);
		Set<State<T>> closedSet = new HashSet<State<T>>();

		while (!openList.isEmpty()) {
			State<T> n = popOpenList();
			//System.out.println(n.getGrade());
			if (s.isGoalState(n)) {
				return new Solution(s.getInitialState(), n);
			}

			closedSet.add(n);

			List<State<T>> neighbors = s.getAllPossibleStates(n);

			for (State<T> neighbor : neighbors) {
				if (!closedSet.contains(neighbor)) {
					neighbor.setCameFrom(n);
					neighbor.setGrade(s.grade(neighbor));
					neighbor.setCost(n.getCost() + neighbor.getStep().getCost());
					if (!openListContains(neighbor)) {
						addToOpenList(neighbor);
					}
					// TODO: relax is slow as hell
//					else if (openList.removeIf(e -> e.equals(neighbor) && e.getCost() > neighbor.getCost())) {
//						addToOpenList(neighbor);
//					}
				}
			}
		}
		return null;
	}
}

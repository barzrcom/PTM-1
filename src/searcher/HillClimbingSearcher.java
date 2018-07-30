package searcher;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import board.Board;
import board.Solution;
import board.State;


public class HillClimbingSearcher extends CommonSearcher {

	public HillClimbingSearcher() {
		super(new PriorityQueue<State<?>>());
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> Solution algoSearch(Searchable<T> s) {
		State<T> currentState = s.getInitialState();
		currentState.setGrade(s.grade(currentState));

		while (true) {
			incEvaluatedNodes();
			//System.out.println(n.getGrade());
			if (s.isGoalState(currentState)) {
				return new Solution(s.getInitialState(), currentState);
			}
			
			List<State<T>> neighbors = s.getAllPossibleStates(currentState);
			for (State<T> neighbor : neighbors) {
				neighbor.setCameFrom(currentState);
			}
			
			if (Math.random() > 0.7) {
				Random r = new Random();
				int randomIndex = r.nextInt(neighbors.size());
				currentState = neighbors.get(randomIndex);
				continue;
			}

			double grade = Double.MAX_VALUE;
			for (State<T> neighbor : neighbors) {	
				neighbor.setGrade(s.grade(neighbor));
				if (neighbor.getGrade() < grade) {
					grade = neighbor.getGrade();
					currentState = neighbor;
				}
			}
		}
	}
}

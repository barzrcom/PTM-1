package searcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import board.Solution;
import board.State;


public class HillClimbingSearcher extends CommonSearcher {
	public HillClimbingSearcher() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> Solution algoSearch(Searchable<T> s) {
		// System.out.println("Using HillClimbing Searcher: ");
		State<T> currentState = s.getInitialState();
		State<T> bestNeighborState = null;

		while (true) {

			List<State<T>> neighbors = new ArrayList<State<T>>(s.getAllPossibleStates(currentState));
			for(State<T> neighbor : neighbors) {
				neighbor.setCameFrom(currentState);
			}
			int grade = 10000;
			if (Math.random() < 0.7) {
				for(State<T> neighbor : neighbors) {
					incEvaluatedNodes();
					int g = s.grade(neighbor);
					if (g < grade) {
						bestNeighborState = neighbor;
						grade = g;
					}
				}
				
				if (bestNeighborState == null)
					bestNeighborState = currentState;
				
				if (s.isGoalState(bestNeighborState)) {
					return new Solution(s.getInitialState(), bestNeighborState);
				}
				
				if (s.grade(currentState) > s.grade(bestNeighborState)) 
				{
					currentState = bestNeighborState;
				}
				else 
				{

				}

			}

			else {
				if(neighbors.isEmpty())
				{
					break;
				}
				Random r = new Random();
				int randomIndex = r.nextInt(neighbors.size());
				currentState = neighbors.get(randomIndex);
			}
		}
		return null;
	}
}
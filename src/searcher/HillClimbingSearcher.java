package searcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import board.Solution;
import board.State;


public class HillClimbingSearcher extends CommonSearcher {
	public HillClimbingSearcher() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Solution algoSearch(Searchable s) {
		// System.out.println("Using HillClimbing Searcher: ");
		State currentState = s.getInitialState();
		State bestNeighborState = null;

		while (true) {

			List<State> neighbors = new ArrayList<State>(s.getAllPossibleStates(currentState));
			for(State neighbor : neighbors) {
				neighbor.setCameFrom(currentState);
			}
			int grade = 10000;
			if (Math.random() < 0.7) {
				for(State neighbor : neighbors) {
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
					bestNeighborState.printState();
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
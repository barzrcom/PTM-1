package solver;

import board.Board;
import board.PipeGameBoard;
import board.SearchablePipeGameBoard;
import board.Solution;
import board.Step;
import searcher.Searcher;

public class PipeSolver implements Solver {
	private Searcher searcher;

	public PipeSolver(Searcher searcher) {
		this.searcher = searcher;
	}

	@Override
	public Solution solve(PipeGameBoard problem) {
//		System.out.println("problem received:");
//		problem.getInitialState().getState().printState();
		SearchablePipeGameBoard sGB = new SearchablePipeGameBoard(problem);
		Solution solution = searcher.search(sGB);
		// reset the searcher to initial state
		searcher.reset();
//        if(solution == null) {
//        	System.out.println("no route could be found");
//        } else {
//            System.out.println("problem answer:");
//            ((Board) solution.getGoalState().getState()).printState();
//            // return solution to the client
//            System.out.println(solution.getStepList().size() + " Steps");
//        	for (Step step : solution.getStepList()) {
//        		System.out.println(step.toString());
//        	}
//        }
        return solution;
	}
}

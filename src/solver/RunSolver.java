package solver;

import board.GameBoard;
import board.PipeGameBoard;
import board.Solution;
import searcher.BFSSearcher;
import searcher.BestFirstSearchSearcher;
import searcher.DFSSearcher;
import searcher.HillClimbingSearcher;

public class RunSolver {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Solver Unittest ****");

        char[][] board0 = {
        		{'s', 'L'},
        		{'F', 'g'}
        };
        char[][] board1 = {	
        		{'s', '|', '|', '|', '|', '|', 'L'},
        		{' ', ' ', ' ', ' ', ' ', ' ', '-'},
        		{' ', ' ', ' ', ' ', 'g', '-', 'L'}
        };
        char[][] board2 = {	
        		{'s', '|', '|', '7', ' '},
        		{' ', ' ', ' ', '-', ' '},
        		{' ', ' ', ' ', '-', ' '},
        		{' ', '|', '|', '7', '7'},
        		{' ', ' ', ' ', ' ', '-'},
        		{' ', ' ', ' ', ' ', '-'},
        		{' ', ' ', ' ', ' ', 'g'},
        		{' ', ' ', ' ', ' ', ' '},
        		{' ', ' ', ' ', ' ', ' '},
        		{' ', ' ', ' ', ' ', ' '}
        };
        
        char[][] board3 = {
        		{'s','-','-','-','-','-','-','L'},
        		{'L','-','|','-','-','|','-','L'},
        		{'L','-','-','-','-','-','-','L'},
        		{'L','-','-','|','-','-','-','g'}
    	};
        char[][] board4 = {
        		{'s','-','-','|','-','-','-','L'},
        		{'L','-','|','-','-','|','-','L'},
        		{'L','-','-','-','J','-','-','L'},
        		{'L','-','-','|','7','-','-','g'}
    	};

        GameBoard gB = new PipeGameBoard(board4);
        
        //Solver solver = new PipeSolver(new BestFirstSearchSearcher());
        //Solver solver = new PipeSolver(new BFSSearcher());
        //Solver solver = new PipeSolver(new DFSSearcher());
        Solver solver = new PipeSolver(new HillClimbingSearcher());
        Solution solution = solver.solve(gB);
        if(solution == null) {
        	System.out.println("no route could be found");
        } else {
        	System.out.println("Steps:");
        	for (String step : solution.getStepsList()) {
        		System.out.println(step);
        	}
        }
    }
}

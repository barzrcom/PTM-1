package solver;

import board.GameBoard;
import board.PipeGameBoard;
import board.Solution;
import searcher.BestFirstSearchSearcher;

public class RunSolver {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Solver Unittest ****");

        //char[][] board = {{'s', 'L'}, {'F', 'g'}};
//        char[][] board = {	
//        		{'s', '|', '|', '|', '|', '|', 'L'},
//        		{' ', ' ', ' ', ' ', ' ', ' ', '-'},
//        		{' ', ' ', ' ', ' ', 'g', '-', 'L'}
//        };
        char[][] board = {	
        		{'s', '|', '|', '7', ' '},
        		{' ', ' ', ' ', '-', ' '},
        		{' ', ' ', ' ', '-', ' '},
        		{' ', ' ', ' ', '7', '7'},
        		{' ', ' ', ' ', ' ', '-'},
        		{' ', ' ', ' ', ' ', 'g'},
        		{' ', ' ', ' ', ' ', ' '},
        		{' ', ' ', ' ', ' ', ' '},
        		{' ', ' ', ' ', ' ', ' '},
        		{' ', ' ', ' ', ' ', ' '}
        };
        GameBoard gB = new PipeGameBoard(board);
        
        Solver solver = new PipeSolver(new BestFirstSearchSearcher());
        Solution sol = solver.solve(gB);
        if(sol == null) {
        	System.out.println("no route could be found");
        } else {
        	// print sol by steps and clicks
        }
    }
}

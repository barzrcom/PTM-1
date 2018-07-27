package solver;

import board.Board;
import board.PipeGameBoard;
import board.Solution;
import board.State;
import searcher.AStarSearcher;

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
        		{'s', '|', '|', '7', '|'},
        		{' ', ' ', ' ', '-', ' '},
        		{' ', ' ', ' ', '-', ' '},
        		{'|', '-', '-', 'J', 'L'},
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
        		{'L','-','|','-','-','|','-','L'},
        		{'L','-','-','-','-','-','-','L'},
        		{'L','-','|','-','-','|','-','L'},
        		{'L','-','-','|','-','-','-','g'}
    	};
        char[][] board4 = {
        		{'s','-','J','|','-','-','-','L'},
        		{'L','-','F','-','-','|','-','L'},
        		{'L','-','-','-','7','-','-','L'},
        		{'L','-','F','-','-','|','-','L'},
        		{'L','-','F','-','-','|','-','L'},
        		{'L','-','-','-','7','-','-','L'},
        		{'L','-','F','-','-','|','-','L'},
        		{'L','-','F','-','-','|','-','L'},
        		{'L','-','-','-','7','-','-','L'},
        		{'L','-','F','-','-','|','-','L'},
        		{'L','-','-','|','J','-','-','g'}
    	};

        char[][] board5 = {
        		{'s','-','L'},
        		{' ',' ','|'},
        		{'L',' ','g'}
    	};
        
        char[][] board7 = {
        		{'s','7','7','7','7','7','7','7','7','7'},
        		{'7','7','7','7','7','7','7','7','7','7'},
        		{'7','7','7','7','7','7','7','7','7','7'},
        		{'7','7','7','7','7','7','7','7','7','7'},
        		{'7','7','7','7','7','7','7','7','7','7'},
        		{'7','7','7','7','7','7','7','7','7','7'},
        		{'7','7','7','7','7','7','7','7','7','7'},
        		{'7','7','7','7','7','7','7','7','7','7'},
        		{'7','7','7','7','7','7','7','7','7','7'},
        		{'7','7','7','7','7','7','7','7','7','7'},
//        		{'7','7','7','7','7','7','7','7','7','7'},
        		{'7','7','7','7','7','7','7','7','7','g'}
    	};


        PipeGameBoard gameBoard = new PipeGameBoard(new Board(board7));
        
        //Solver solver = new PipeSolver(new BestFirstSearchSearcher());
//        Solver solver = new PipeSolver(new BFSSearcher());
        //Solver solver = new PipeSolver(new DFSSearcher());
        Solver solver = new PipeSolver(new AStarSearcher());
        //Solver solver = new PipeSolver(new HillClimbingSearcher());
        Solution solution = solver.solve(gameBoard);
        State<Board> myState =  (State<Board>) solution.getGoalState();
        myState.getState().printState();

    }
}

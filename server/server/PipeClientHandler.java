package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PipeGameBoard;
import board.Solution;
import board.Step;
import cacheManager.CacheManager;
import cacheManager.FileCacheManager;
import searcher.AStarSearcher;
import solver.PipeSolver;
import solver.Solver;

/**
 * has the responsibility of closing the streams
 */
public class PipeClientHandler implements ClientHandler {
	
	private CacheManager cacheManager = new FileCacheManager();
	
	@Override
	public Board inClient(InputStream inFromClient) {
        BufferedReader inFClient = new BufferedReader(new InputStreamReader(inFromClient));
    	List<char[]> inputFromClient = new ArrayList<char[]>();
        String line;
        try {
			while (!(line = inFClient.readLine()).equals("done")) {
				inputFromClient.add(line.toCharArray());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // build State from message.
        char[][] charArray = inputFromClient.toArray(new char[inputFromClient.size()][]);
        Board board = new Board(charArray);
        System.out.println("Board size: " + board.getBoardX() + "X" + board.getBoardY());
		return board;
	}


	@Override
	public void outClient(OutputStream outToClient, Board board) {
		// Solver has to be local!
		Solver solver = new PipeSolver(new AStarSearcher());
		
		PipeGameBoard gameBoard = new PipeGameBoard(board);
		PrintWriter outTC = new PrintWriter(outToClient);
		// Check if state-solve exists in cache manager
		Solution solution;
		if (cacheManager.isSolutionExist(gameBoard.getInitialState())) {
			// load solution from cache manager
			solution = this.cacheManager.load(gameBoard.getInitialState());
		} else {
			// send it to solver and save it in cache manager
			solution = solver.solve(gameBoard);
			cacheManager.save(gameBoard.getInitialState(), solution);
		}
		if (solution == null) {
			System.out.println("no route could be found");
		} else {
			for (Step step : solution.getStepList()) {
				outTC.println(step.toString());
			}
		}

		outTC.println("done");
		outTC.flush();
		// close the connection
		outTC.close();
		
	}
}

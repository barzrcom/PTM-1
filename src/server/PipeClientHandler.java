package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import board.GameBoard;
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
	private Solver solver = new PipeSolver(new AStarSearcher());


    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        PrintWriter outTC = new PrintWriter(outToClient);
        BufferedReader inFClient = new BufferedReader(new InputStreamReader(inFromClient));
        try {
        	List<char[]> inputFromClient = new ArrayList<char[]>();
            String line;
            while (!(line = inFClient.readLine()).equals("done")) {
            	inputFromClient.add(line.toCharArray());
            }
            
            // build State from message.
            char[][] charArray = inputFromClient.toArray(new char[inputFromClient.size()][]);
			GameBoard gameBoard = new PipeGameBoard(charArray);
			System.out.println("problem received:");
			gameBoard.getInitialState().printState();
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
            
            // return solution to the client
        	for (Step step : solution.getStepList()) {
        		outTC.println(step.toString());
        		System.out.println(step.toString());
        	}
        	outTC.flush();

            // close the connection
            inFClient.close();
            outTC.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

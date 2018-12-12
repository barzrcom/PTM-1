package test;


import board.SearchablePipeGameBoard;
import board.Solution;
import board.State;
import board.Step;
import cacheManager.CacheManager;
import cacheManager.FileCacheManager;
import searcher.BestFirstSearchSearcher;
import searcher.Searchable;
import searcher.Searcher;
import server.ClientHandler;
import server.PipeClientHandler;
import server.PipeServer;
import server.Server;
import solver.PipeSolver;
import solver.Solver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// edit these imports according to your project

public class TestSetter {

	// run your server here
	static Server s;

	public static void runServer(int port) {
		s = new PipeServer(port);
		s.start(new PipeClientHandler());
	}

	public static void setClasses(DesignTest dt) {

		// set the server's Interface, e.g., "Server.class"
		// don't forget to import the correct package e.g., "import server.Server"
		dt.setServerInteface(Server.class);
		// now fill in the other types according to their names
		// server's implementation
		dt.setServerClass(PipeServer.class);
		// client handler interface
		dt.setClientHandlerInterface(ClientHandler.class);
		// client handler class
		dt.setClientHandlerClass(PipeClientHandler.class);
		// cache manager interface
		dt.setCacheManagerInterface(CacheManager.class);
		// cache manager class
		dt.setCacheManagerClass(FileCacheManager.class);
		// solver interface
		dt.setSolverInterface(Solver.class);
		// solver class
		dt.setSolverClass(PipeSolver.class);
		// searchable interface
		dt.setSearchableInterface(Searchable.class);
		// searcher interface
		dt.setSearcherInterface(Searcher.class);
		// your searchable pipe game class
		dt.setPipeGameClass(SearchablePipeGameBoard.class);
		// your Best First Search implementation
		dt.setBestFSClass(BestFirstSearchSearcher.class);
	}

	/* ------------- Best First Search Test --------------
	 * You are given a Maze
	 * Create a new Searchable from the Maze
	 * Solve the Maze
	 * and return a list of moves from {UP,DOWN,RIGHT,LEFT}
	 *
	 */
	public static List<String> solveMaze(Maze m) {


		Searchable<Grid> searchable = new Searchable<Grid>() {
			@Override
			public List<State<Grid>> getAllPossibleStates(State<Grid> s) {
				List<State<Grid>> stateList = new ArrayList<State<Grid>>();
				List<Grid> gridList = m.getPossibleMoves(s.getState());
				// calc the steps to our state
				for (Grid grid : gridList) {
					List<String> actionList = new ArrayList<String>();
					if (grid.row > s.getState().row) {
						actionList.add("DOWN");
					}
					if (grid.row < s.getState().row) {
						actionList.add("UP");
					}
					if (grid.col > s.getState().col) {
						actionList.add("RIGHT");
					}
					if (grid.col < s.getState().col) {
						actionList.add("LEFT");
					}
					String actions = String.join(",", actionList);
					stateList.add(new State<Grid>(grid, new Step() {
						@Override
						public String ToString() {
							return actions;
						}
					}));
				}
				return stateList;
			}

			@Override
			public State<Grid> getInitialState() {
				return new State<Grid>(m.getEntrance());
			}

			@Override
			public double grade(State<Grid> state) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean isGoalState(State<Grid> s) {
				double distance = Point.distance(s.getState().col, s.getState().row, m.getExit().col, m.getExit().row);
				// when distance from the goal is 1 direct or sqrt(2) for diagonal
				return distance == 1.0 || distance == Math.sqrt(2);
			}
		};


		Searcher searcher = new BestFirstSearchSearcher();

		Solution solution = searcher.search(searchable);

		// add final grid manually to the states
		List<Step> stepList = solution.getStepList();
		List<String> actionList = new ArrayList<String>();
		for (Step step : stepList) {
			String actions = step.toString();
			String[] spilitedAction = actions.split(",");
			for (String action : spilitedAction) {
				actionList.add(action);
			}
		}
		// add last action due to lack of final step in the getAllPossibleStates function
		Grid finalGrid = (Grid) solution.getGoalState().getState();
		if (m.getExit().row > finalGrid.row) {
			actionList.add("DOWN");
		}
		if (m.getExit().row < finalGrid.row) {
			actionList.add("UP");
		}
		if (m.getExit().col > finalGrid.col) {
			actionList.add("RIGHT");
		}
		if (m.getExit().col < finalGrid.col) {
			actionList.add("LEFT");
		}

		return actionList;

	}

	// stop your server here
	public static void stopServer() {
		s.stop();
	}

}

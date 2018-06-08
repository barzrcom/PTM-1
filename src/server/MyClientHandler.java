package server;

import java.io.*;

/**
 * has the responsibility of closing the streams
 */
public class MyClientHandler implements ClientHandler {
	private CacheManager cacheManager;
	private Solver solver; 

	MyClientHandler(){
		cacheManager = new MyCacheManager();
		solver = new MySolver();
	}

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        PrintWriter outTC = new PrintWriter(outToClient);
        BufferedReader inFClient = new BufferedReader(new InputStreamReader(inFromClient));
        try {
        	// TODO: read message from client
        	StringBuilder allLines = new StringBuilder();
            String line;
            while (!(line = inFClient.readLine()).equals("done")) {
            	allLines.append(line);
            }
            
            // build State from message.
			State clientState = new State(allLines.toString());
            
			// Check if state-solve exists in cache manager
            State solutionState;
            if (this.cacheManager.isSolutionExist(clientState)) {
            	// load solution from cache manager
            	solutionState = (State)this.cacheManager.load(clientState);
            } else {
            	// send it to solver and save it in cache manager
            	solutionState = this.solver.solve(clientState);
            	this.cacheManager.save(clientState, solutionState);
            }
            
            // return solve to the client
            outTC.write(solutionState.getState().toString());
            outTC.flush();

            // close the connection
            inFClient.close();
            outTC.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

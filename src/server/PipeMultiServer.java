package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import board.State;

public class PipeMultiServer extends MultiServer {
    private int port;
    private ServerSocket serverSocket;
    volatile private boolean stop = false;
    volatile private BlockingQueue<Socket> clientSocketQueue;
    
    public PipeMultiServer(int port, int numberOfThreads) {
    	super(Executors.newFixedThreadPool(numberOfThreads));
        this.clientSocketQueue = new PriorityBlockingQueue<Socket>(5,
        		//Socket Comparator (available is amount of data ready on socket)
        		(Socket o1, Socket o2)->{
					try {
						return o1.getInputStream().available() - o2.getInputStream().available();
					} catch (IOException e) {
						return 0;
					}
				});
    	this.port = port;
    }

    @Override
    public void start(ClientHandler clientHandler) {
        new Thread(() -> {
            try {
                startServer(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startServer(ClientHandler clientHandler) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(5000);
        System.out.println("Server started - waiting");

		new Thread(() -> {
			while (!stop) {
				try {
					// Waiting for a client
					Socket aClient = serverSocket.accept();
					System.out.println("client connected");
					clientSocketQueue.add(aClient);
				} catch (IOException e) {
					e.printStackTrace();
				}

		}}).start();
        
		// handle any socket
        while (!stop || !clientSocketQueue.isEmpty()) {
        	if(!clientSocketQueue.isEmpty()) {
        		Socket aClient = clientSocketQueue.poll();
        		es.submit(()-> {
					try {
		                System.out.println("handle client");
		                clientHandler.handleClient(aClient.getInputStream(), aClient.getOutputStream());
		                aClient.close();
		                System.out.println("client disconnected");
		            } catch (SocketTimeoutException e) {
		            	e.printStackTrace();
		            } catch (IOException e) {
						e.printStackTrace();
					}
				});
        	}
        }
        
        serverSocket.close();
        System.out.println("Finish handling last clients");
        es.shutdown();
        System.out.println("Done");
    }

    @Override
    public void stop() {
        stop = true;
    }
}

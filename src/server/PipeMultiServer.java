package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import board.State;

public class PipeMultiServer extends MultiServer {
    private int port;
    private ServerSocket serverSocket;
    volatile private boolean stop = false;
    ThreadPoolExecutor tpe;
    
    public PipeMultiServer(int port, int numberOfThreads) {
    	this.port = port;
    	this.tpe = new ThreadPoolExecutor(1, numberOfThreads, 10, TimeUnit.SECONDS,
    			new PriorityBlockingQueue<Runnable>());
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


		while (!stop) {
			try {
				// Waiting for a client
				Socket aClient = serverSocket.accept();
				System.out.println("client connected");
				int priority = 0;
				do {
					priority = aClient.getInputStream().available();
				} while (priority == 0);

				System.out.println("Client priority: " + priority);
				tpe.execute(new PriorityRunnable(priority) {
					@Override
					public void run() {
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
					}
				});
			} catch (IOException e) {

			}
		}

        serverSocket.close();
        System.out.println("Finish handling last clients");
        tpe.shutdown();
  
        try {
			tpe.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        System.out.println("Done");
    }

    @Override
    public void stop() {
        stop = true;
    }
}

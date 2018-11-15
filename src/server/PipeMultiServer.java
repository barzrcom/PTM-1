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
    	BlockingQueue<Runnable> pq = new PriorityBlockingQueue<Runnable>(5, new ComparePriority());
    	this.tpe = new ThreadPoolExecutor(1, numberOfThreads, 10, TimeUnit.SECONDS, pq);
    	this.port = port;
    }

    private static class ComparePriority<T extends PriorityRunnable> implements Comparator<T> {

        @Override
        public int compare(T o1, T o2) {
        	System.out.println(o1.getPriority() + ":" + o2.getPriority());
            return o1.getPriority() - o2.getPriority();
        }
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
				tpe.execute(new PriorityRunnable(aClient.getInputStream().available()) {
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
			while(!tpe.awaitTermination(5, TimeUnit.SECONDS)) {
			
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Done");
    }

    @Override
    public void stop() {
        stop = true;
    }
}

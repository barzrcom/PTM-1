package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import board.Board;

public class PipeMultiServer extends MultiServer {
    private int port;
    private ServerSocket serverSocket;
    volatile private boolean stop = false;
    ThreadPoolExecutor tpe;
    
    public PipeMultiServer(int port, int numberOfThreads) {
    	this.port = port;
    	this.tpe = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 5, TimeUnit.SECONDS,
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
        System.out.println("Multi server started - waiting");
		while (!stop) {
			try {
				// Waiting for a client
				Socket clientSocket = serverSocket.accept();
				System.out.println("client connected");
				InputStream clientInputStream = clientSocket.getInputStream();
				// Base assumption -> Client input is VALID (defined by course's lecturer)
                Board board = clientHandler.inClient(clientInputStream);
                int priority = board.getBoardX() * board.getBoardY();
				System.out.println("Client priority: " + priority);
				tpe.execute(new PriorityRunnable(priority) {
					@Override
					public void run() {
						try {
			                System.out.println("handle client");
			                OutputStream clientOutputStream = clientSocket.getOutputStream();
			                clientHandler.outClient(clientOutputStream, board);
			                // InputStream.close() close the socket! close it only at the end
			                clientInputStream.close();
			                clientOutputStream.close();
			                clientSocket.close();
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

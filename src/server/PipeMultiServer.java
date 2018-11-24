package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.util.Pair;

public class PipeMultiServer implements Server {
    private int port;
    private ServerSocket serverSocket;
    private boolean stop = false;
    private PriorityBlockingQueue<Pair<ClientHandler, Socket>> queue;

    public PipeMultiServer(int port) {
        this.port = port;
        
        Comparator<Pair<ClientHandler, Socket>> boardSizeComparator = new Comparator<Pair<ClientHandler, Socket>>() {
            public int compare(Pair<ClientHandler, Socket> o1, Pair<ClientHandler, Socket> o2) {
                return o1.getKey().getSize() - o2.getKey().getSize();
            }
        };
        
        this.queue = new PriorityBlockingQueue<Pair<ClientHandler, Socket>>(100, boardSizeComparator);
    }

    @Override
    public void start(ClientHandler clientHandler) throws IOException {
    	serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(5000);
        System.out.println("Server started - waiting");
        
        new Thread(() -> {
            try {
            	this.receiveClients(serverSocket, clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        
        handleQueue();
        serverSocket.close();
    }
    
    private void receiveClients(ServerSocket serverSocket, ClientHandler clientHandler) throws IOException {
        System.out.println("receiveClients - Start handle clients");

        while (!stop) {
            try {
                Socket aClient = serverSocket.accept();
                System.out.println("client connected");
                clientHandler.inClient(aClient.getInputStream());
             // receive clients in and add it in the queue
                this.queue.add(new Pair<ClientHandler, Socket> (clientHandler, aClient));
             // continue handle new connections.
            } catch (SocketTimeoutException e) {
                
            }
        }
    }
    
    private void handleQueue() throws IOException {
        System.out.println("handleQueue method");

        while (!stop) {          
        	new Thread(() -> {
        		  System.out.println("Waiting for clients to be added to queue...");

        		  try {
        			// poll out the client from the queue (Priority queue)
        			  Pair<ClientHandler, Socket> poll = this.queue.take();
        		      Socket aClient = poll.getValue();
        		      ClientHandler clientHandler = poll.getKey();
        		      try {
        	                clientHandler.outClient(aClient.getOutputStream());
        	                
        	                aClient.close();
        	                System.out.println("client disconnected");
        	            } catch (SocketTimeoutException e) {
        	                
        	            } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        		  } catch (InterruptedException e) {
        		      e.printStackTrace();
        		  }
        		}).start();
        	try {
				TimeUnit.SECONDS.sleep(1);  // TODO: remove sleep
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    @Override
    public void stop() {
        stop = true;
    }
}

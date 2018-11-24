package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import board.Board;

public class PipeServer implements Server {
    private int port;
    private ServerSocket serverSocket;
    private boolean stop = false;

    public PipeServer(int port) {
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

        while (!stop) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");
                InputStream clientInputStream = clientSocket.getInputStream();
                Board board = clientHandler.inClient(clientInputStream);
                OutputStream clientOutputStream = clientSocket.getOutputStream();
                clientHandler.outClient(clientOutputStream, board);
                // InputStream.close() close the socket!
                clientInputStream.close();
                clientOutputStream.close();
                clientSocket.close();
                System.out.println("client disconnected");
            } catch (SocketTimeoutException e) {
                
            }
        }
        serverSocket.close();
    }

    @Override
    public void stop() {
        stop = true;
    }
}

package server;

import java.io.InputStream;
import java.io.OutputStream;

import board.Board;

public interface ClientHandler {
    Board inClient(InputStream inFromClient);
    void outClient(OutputStream outToClient, Board board);
}

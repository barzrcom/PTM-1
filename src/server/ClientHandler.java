package server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
    void handleClient(InputStream inFromClient, OutputStream outToClient);
    void inClient(InputStream inFromClient);
    void outClient(OutputStream outToClient);
    
    public default int getSize() {
		return 0;
	}
}

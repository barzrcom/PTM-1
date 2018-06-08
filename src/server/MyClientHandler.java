package server;

import java.io.*;

/**
 * has the responsibility of closing the streams
 */
public class MyClientHandler implements ClientHandler {

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        PrintWriter outTC = new PrintWriter(outToClient);
        BufferedReader inFClient = new BufferedReader(new InputStreamReader(inFromClient));
        try {
            String line;
            while (!(line = inFClient.readLine()).equals("done")) {
                String reverse = new StringBuilder(line).reverse().toString();
                outTC.println(reverse);
                outTC.flush();
            }
            inFClient.close();
            outTC.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

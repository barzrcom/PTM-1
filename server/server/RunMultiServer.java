package server;

public class RunMultiServer {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Server Side ****");

        /* Signature:
        *  PipeMultiServer(portNumber, numberOfThreads)
        */
        Server s = new PipeMultiServer(6400, 5);
        s.start(new PipeClientHandler());
        System.in.read();
        s.stop();
        System.out.println("Closed server");
    }
}

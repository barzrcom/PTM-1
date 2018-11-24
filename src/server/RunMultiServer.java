package server;

public class RunMultiServer {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Server Side ****");

        Server s = new PipeMultiServer(6400, 5);//Take the port from the args
        s.start(new PipeClientHandler());
        System.in.read();
        s.stop();
        System.out.println("Closed server");
    }
}

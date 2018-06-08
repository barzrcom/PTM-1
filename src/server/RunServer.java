package server;

public class RunServer {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Server Side ****");

//        Server s = new MyServer(Integer.parseInt(args[0]));//Take the port from the args
        Server s = new MyServer(6400);//Take the port from the args
        s.start(new MyClientHandler());
        System.in.read();
        s.stop();
        System.out.println("Closed server");
    }
}

package server;

import java.util.concurrent.ExecutorService;



public abstract class MultiServer implements Server {
	protected ExecutorService es;
	public MultiServer(ExecutorService es){
		this.es = es;
	}
}

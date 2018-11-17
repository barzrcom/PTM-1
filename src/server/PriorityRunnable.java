package server;


public abstract class PriorityRunnable implements Comparable<PriorityRunnable>, Runnable {
	int priority;
	
	@Override
	public int compareTo(PriorityRunnable o) {
		return this.priority - o.priority;
	}

	public PriorityRunnable(int priority){
		this.priority = priority;
	}

}
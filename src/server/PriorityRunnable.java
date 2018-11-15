package server;

import java.util.Comparator;

public abstract class PriorityRunnable implements Runnable {
	int priority;
	
	public int getPriority() {
		return priority;
	}

	public PriorityRunnable(int priority){
		this.priority = priority;
	}

}
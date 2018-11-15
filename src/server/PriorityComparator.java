package server;

import java.util.Comparator;

public class PriorityComparator<T extends PriorityRunnable> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
    	System.out.println(o1.getPriority() + ":" + o2.getPriority());
        return o1.getPriority() - o2.getPriority();
    }
}
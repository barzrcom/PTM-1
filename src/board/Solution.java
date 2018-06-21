package board;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Solution {
	List<String> stepsList;
	public Solution() {
		stepsList = new LinkedList<String>();
	}
	public void addStep(String step) {
		stepsList.add(step);
	}
	public List<String> getStepsList() {
		return stepsList;
	}
	public void reverse() {
		Collections.reverse(stepsList);
	}
}

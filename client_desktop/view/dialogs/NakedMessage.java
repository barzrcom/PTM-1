package view.dialogs;

import java.util.ArrayList;
import java.util.List;

public class NakedMessage {
	
	private final List<String> messageList = new ArrayList<>();
	
	public NakedMessage(String message) {
		this.messageList.add(message);
	}
	
	public void addMessage(String message) {
		this.messageList.add(message);
	}
	
	List<String> getMessageList() {
		return messageList;
	}
	
}

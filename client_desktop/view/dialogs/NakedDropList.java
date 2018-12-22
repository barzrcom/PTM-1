package view.dialogs;

import javafx.collections.ObservableList;

public interface NakedDropList {
	ObservableList<String> options = null;

	ObservableList<String> getOptions();

	void setOptions(ObservableList<String> list);
}

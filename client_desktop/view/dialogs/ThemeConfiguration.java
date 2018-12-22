package view.dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A rather naive implementation of the naked object interface. A "better" implementation would hae the NakedObjectDisplayer
 * use reflection to get the class's fields and display them, thus making all the boilerplate here redundant.
 * That does entail some voodoo though, and voodoo for something as simple as this may not be necessary.
 *
 * @author giladber
 */
public class ThemeConfiguration implements NakedDropList {

	// Default values here
	private ObservableList<String> options;

	public ThemeConfiguration() {
		this.options = FXCollections.observableArrayList("mario", "olympics");
	}

	@Override
	public ObservableList<String> getOptions() {
		return this.options;
	}

	@Override
	public void setOptions(ObservableList<String> list) {
		this.options = list;
	}
}

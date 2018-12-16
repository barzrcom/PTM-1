package view.dialogs;

/**
 * A rather naive implementation of the naked object interface. A "better" implementation would hae the NakedObjectDisplayer
 * use reflection to get the class's fields and display them, thus making all the boilerplate here redundant.
 * That does entail some voodoo though, and voodoo for something as simple as this may not be necessary.
 * @author giladber
 *
 */
public class ThemeConfiguration implements NakedObject {
	
	// Default values here
	public String Theme = "Mario";
	
}

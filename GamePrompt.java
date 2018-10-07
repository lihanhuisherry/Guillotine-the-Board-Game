package Indy;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * A concrete subclass of the Guillotine prompt that is used to display generic
 * prompts
 *
 * @param <T>
 *            the type of Objects this class of prompts display, usually Integer
 *            and Player
 */
public class GamePrompt<T> extends GuillotinePrompt<T> {

	/**
	 * Constructs a GamePrompt based on some parameters, just like GuillotinePrompt
	 * 
	 * @param game,
	 *            the JavaFXGame the Prompt is used for
	 * @param title,
	 *            the title of this Prompt
	 * @param stuff,
	 *            the stuff to be displayed inside this prompt.
	 */
	public GamePrompt(JavaFXGame game, String title, T[] stuff) {
		super(game, title, stuff);
		// Default choice
		result = stuff[0];
	}

	@Override
	protected void initPane(T[] stuff) {
		int i = 0;
		for (T item : stuff) {
			Button but = new Button(item.toString());
			_floater.getChildren().add(but);
			but.setFocusTraversable(false);
			but.relocate((i++) * FACTOR_Y, 0);
			tools.add(but);
			but.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					result = item;
					_pane.getChildren().removeAll(tools);
					hide();
				}
			});
		}
	}
}

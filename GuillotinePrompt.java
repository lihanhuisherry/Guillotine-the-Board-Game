package Indy;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * An abstract class that pops up a window, which is later called showAndWait()
 * on to allow user interaction.
 *
 * @param <T>
 *            the type of Objects the Prompt needs to display
 */
public abstract class GuillotinePrompt<T> extends Stage {

	protected static final int FACTOR_X = Constants.PROMPT_FACTOR_X;
	protected static final int FACTOR_Y = Constants.PROMPT_FACTOR_Y;
	protected ArrayList<Button> tools;
	protected Pane _pane;
	protected Pane _floater;
	protected JavaFXGame game;
	protected T result;

	/**
	 * Constructs a GuilltoinePrompt with the given paramters
	 * 
	 * @param game,
	 *            the JavaFXGame the Prompt is used for
	 * @param title,
	 *            the title of this Prompt
	 * @param stuff,
	 *            the stuff to be displayed inside this prompt.
	 */
	public GuillotinePrompt(JavaFXGame game, String title, T[] stuff) {
		super();
		this.tools = new ArrayList<Button>();
		this.game = game;
		PaneOrganizer organizer = game.getOrganizer();
		this._pane = organizer.getDisp();
		this.initOwner(organizer.getMain());
		this._floater = new Pane();
		this.setScene(new Scene(_floater, Constants.PROMPT_WIDTH, Constants.PROMPT_HEIGHT));
		this.setX(Constants.PROMPT_X);
		this.setY(Constants.PROMPT_Y);
		this.setTitle(title);
		this.initPane(stuff);
	}

	/**
	 * Initializes the pane inside the prompt with the stuff
	 * 
	 * @param stuff,
	 *            the stuff to show
	 */
	protected abstract void initPane(T[] stuff);

	public T get() {
		return result;
	}

}

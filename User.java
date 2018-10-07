package Indy;

import com.sun.javafx.tk.Toolkit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * A concrete implementation of the Player interface, through the abstract class
 * Game. This is implemented via Java FX
 */
@SuppressWarnings("restriction")
public class User extends AbPlayer {

	private JavaFXGame _game;

	/**
	 * Constructs a User with the game it's in and its name
	 * 
	 * @param game,
	 *            the JavaFXGame this User is in
	 * @param name,
	 *            the name of this User
	 */
	public User(JavaFXGame game, String name) {
		super(game, name);
		this._game = game;
	}

	@Override
	public Player selectPlayer(boolean opponent) {
		return _game.playerSelectPlayer(opponent, this);
	}

	@Override
	public Noble selectNoble(Noble[] nobles) {
		return _game.playerSelectNoble(nobles, toString());
	}

	@Override
	public Action selectAction(Player player) {
		return _game.playerSelectAction(player, toString());
	}

	@Override
	public void selectAndPlay() {
		Prompt<Action> prompt = new Prompt<Action>(_game, this + ", please select an Action card to play",
				getActions(true));
		prompt.showAndWait();
		Action selected = prompt.get();
		if (selected != null) {
			hand.remove(selected);
			selected.uponAction(this);
		}
	}

	@Override
	public Global selectGlobal(Player player) {
		return _game.playerSelectGlobal(player, toString());
	}

	@Override
	public int selectInt(int[] moves) {
		return _game.playerSelectInt(moves, toString());
	}

	@Override
	public void hint(String message, Card... cards) {
		Button button = new Button(toString() + ", click this to proceed.");
		_game.getOrganizer().getDisp().getChildren().add(button);
		button.relocate(Constants.USERSCREEN_WIDTH * 0.5, Constants.DOWNROOT_HEIGHT * 0.5);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				_game.getOrganizer().getDisp().getChildren().remove(button);
		        Toolkit.getToolkit().exitNestedEventLoop(button, null);
			}
		});
        Toolkit.getToolkit().enterNestedEventLoop(button);
		/*
		Stage blocker = new Stage();
		Pane pane = new Pane();

		Button button = new Button(toString() + ", click this to proceed.");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				blocker.hide();
			}
		});
		pane.getChildren().add(button);
		int i = 0;
		for (Card card : cards) {
			JavaFXCard<Card> fxCard = new JavaFXCard<Card>(card, pane);
			fxCard.setActionPosition(i++);
			pane.getChildren().add(fxCard.getView());
		}

		blocker.setScene(new Scene(pane, Constants.USERSCREEN_WIDTH, Constants.USERSCREEN_HEIGHT));
		blocker.setTitle(toString() + message);
		blocker.showAndWait();
		*/
	}

}

package Indy;

import java.util.Arrays;

/**
 * A concrete implementation of the Guillotine game, through the abstract class
 * Game. This is implemented via Java FX.
 */
public class JavaFXGame extends Game implements Runnable {

	private final PaneOrganizer _organizer;
	private GuillotinePrompt<?> _prompt;
	private Runnable _monitor;

	/**
	 * Constructs a JavaFXGame with a PaneOrganizer and some Strings
	 * representing the names of the players in this game. Null Strings
	 * represent AI's
	 *
	 * @param organizer,
	 *            the PaneOrganizer that holds the Panes
	 * @param names,
	 *            the names of the participating players
	 */
	public JavaFXGame(PaneOrganizer organizer, String[] names) {
		super();
		this._organizer = organizer;
		for (String name : names) {
			players.add(name.equals("AI") ? new GuillotineAI(this) : new User(this, name));
		}
	}

	@Override
	public void run() {
		nextDay();
	}

	@Override
	public void displayQueue() {
		_organizer.updateNobles(queue);
	}

	@Override
	protected void gameOver() {
		players.sort(this);
		int i = 0;
		String[] arr = new String[players.size()];
		for (Player player : players) {
			arr[i++] = String.format("%s ranks No.%d with %d points.", player, i, player.getScore());
		}
		_organizer.gameOver(arr);
	}

	/**
	 * Makes a Prompt with this JavaFX game. This is just a simple helper method
	 *
	 * @param title,
	 *            the title of the Prompt
	 * @param toDisp,
	 *            the things to display in the Prompt
	 * @return the constructed prompt
	 */
	private <T extends Card> Prompt<T> prompt(String title, T[] toDisp) {
		return new Prompt<T>(this, title, toDisp);
	}

	/**
	 * Makes a GamePrompt with this JavaFX game. This is just a simple helper.
	 *
	 * @param title,
	 *            the title of the GamePrompt
	 * @param toDisp,
	 *            the things to display in the GamePrompt
	 * @return the constructed game prompt
	 */
	private <T> GamePrompt<T> gamePrompt(String title, T[] toDisp) {
		return new GamePrompt<T>(this, title, toDisp);
	}

	/**
	 * Prompt the selector to select an Action from the hand of the given
	 * Player, if the Player is null, it means selecting from the discard
	 *
	 * @param p,
	 *            the given Player
	 * @param selectorName,
	 *            the name of the selector to be printed on the prompt
	 * @return the selected Action (must not be null)
	 */
	public Action playerSelectAction(Player p, String selectorName) {
		Action[] selectFrom = p == null ? discarded.toArray(new Action[0]) : p.getActions(false);
		Prompt<Action> prompt = prompt(selectorName + ", please select an Action card", selectFrom);
		prompt.showAndWait();
		return prompt.getOrDefault(selectFrom[0]);
	}

	/**
	 * Prompt the selector to select a Global from the globals of given Player
	 *
	 * @param p,
	 *            the given Player
	 * @param selectorName,
	 *            the name of the selector to be printed on the prompt
	 * @return the selected Global (which might be null)
	 */
	public Global playerSelectGlobal(Player p, String selectorName) {
		Global[] globals = p.getGlobals();
		Prompt<Global> prompt = prompt(selectorName + ", please select a Support card", globals);
		prompt.showAndWait();
		return prompt.get();
	}

	/**
	 * Prompt the selector to select a Noble from a non-empty array of Nobles
	 *
	 * @param selectFrom
	 *            an given array of Nobles to select the Noble from
	 * @param selectorName,
	 *            the name of the selector to be printed on the prompt
	 * @return the selected Noble (must not be null)
	 */
	public Noble playerSelectNoble(Noble[] selectFrom, String selectorName) {
		Prompt<Noble> prompt = prompt(selectorName + ", please select a Noble card", selectFrom);
		prompt.showAndWait();
		return prompt.getOrDefault(selectFrom[0]);
	}

	/**
	 * Prompt the selector to select a Player from the game
	 *
	 * @param opponent,
	 *            specifying whether the player has to choose an opponent
	 * @param selector,
	 *            a reference to the selector
	 * @return the selected Player, which cannot be null
	 */
	public Player playerSelectPlayer(boolean opponent, Player selector) {
		int length = players.size();
		Player[] selectFrom = new Player[opponent ? --length : length];
		int i = 0;
		for (Player player : players) {
			if (opponent && player == selector) {
				continue;
			}
			selectFrom[i++] = player;
		}
		String message = String.format("%s, please select a %s", selector, opponent ? "n opponent" : " player");
		GamePrompt<Player> prompt = gamePrompt(message, selectFrom);
		prompt.showAndWait();
		return prompt.get();
	}

	/**
	 * Prompt the selector to select an integer from a non-empty integer array
	 *
	 * @param moves,
	 *            specifying the set of integers to choose from
	 * @param selectorName,
	 *            the name of the player making the selection
	 * @return the selected integer
	 */
	public int playerSelectInt(int[] moves, String selectorName) {
		Integer[] selectFrom = Arrays.stream(moves).boxed().toArray(Integer[]::new);
		GamePrompt<Integer> prompt = gamePrompt(selectorName + ", please select a number of steps to move", selectFrom);
		prompt.showAndWait();
		return prompt.get();
	}

	/**
	 * Gets the corresponding PaneOrganizer
	 *
	 * @return the cached reference to the PaneOrganizer of this game
	 */
	public PaneOrganizer getOrganizer() {
		return this._organizer;
	}
}

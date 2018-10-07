package Indy;

/**
 * The overall interface for the Players of the Guillotine game
 */
public interface Player {

	/**
	 * Gets the Action cards of this player as an array of Action
	 * 
	 * @param toPlay,
	 *            a boolean specifying whether this precludes Action cards
	 *            without legal targets
	 * @return an Action[] of the Action cards that this Player has
	 */
	Action[] getActions(boolean toPlay);

	/**
	 * Gets the Global cards of this player as an array of Global
	 * 
	 * @return an Global[] of the Global cards that this Player has
	 */
	Global[] getGlobals();

	/**
	 * Gets the Support cards of this player as an array of Support
	 * 
	 * @return an Support[] of the Support cards that this Player has
	 */
	Support[] getSupports();

	/**
	 * Gets the Noble cards of this player as an array of Noble
	 * 
	 * @return an Noble[] of the Noble cards that this Player has
	 */
	Noble[] getCollected();

	int getScore();

	Guillotine getGame();

	/**
	 * Selects a player in this game
	 * 
	 * @param opponent,
	 *            a boolean specifying whether the player has to select an
	 *            opponent
	 * 
	 * @return the selected Player, must not be null
	 */
	Player selectPlayer(boolean opponent);

	/**
	 * Selects an Action card from the given player, if a null reference is
	 * passed, select from the discard pile
	 * 
	 * @param player,
	 *            target player from whom to extract an Action card
	 * 
	 * @return the selected Action card, must not be null
	 */
	Action selectAction(Player player);

	/**
	 * Selects a Global card from the given player
	 * 
	 * @param player,
	 *            target player from whom to extract a Global card
	 * 
	 * @return the selected Global card, might be null
	 */
	Global selectGlobal(Player player);

	/**
	 * Selects an Noble card from an array of nobles
	 * 
	 * @param nobles,
	 *            the array of Nobles to choose from
	 * 
	 * @return the selected Noble card, must not be null
	 */
	Noble selectNoble(Noble[] nobles);

	/**
	 * Selects an integer from the given set
	 * 
	 * @param nobles,
	 *            the set of possible integers as an array
	 * 
	 * @return the selected integer
	 */
	int selectInt(int[] moves);

	/**
	 * Discard and return the discarded Noble card
	 * 
	 * @param random,
	 *            a boolean specifying whether the Noble is discarded at random
	 * @return the discarded Noble, or null if this Player has none
	 */
	Noble discardNoble(boolean random);

	/**
	 * Draws the given action cards, null references denote draws from the
	 * Deck<Action> in the game
	 * 
	 * @param actions,
	 *            the cards to be drawn
	 */
	void draw(Action... actions);

	/**
	 * Selects an Action card that has some legal targets that this player has.
	 * Then play that Action card and execute its effects
	 */
	void selectAndPlay();

	/**
	 * Collects a Noble from the line of Nobles from the game
	 */
	void collect();

	/**
	 * Adds the given noble to this Player's hand
	 * 
	 * @param noble,
	 *            the Noble to be added
	 */
	void getNoble(Noble noble);

	/**
	 * Adds the given Support - Global pair to this Player's pile
	 * 
	 * @param support,
	 *            the Support card to be added
	 * @param g,
	 *            the corresponding Global card to be added
	 */
	void addGlobal(Support support, Global g);

	/**
	 * Discard the given Global card
	 * 
	 * @param global,
	 *            the Global to be discarded
	 */
	void discardGlobal(Global global);

	/**
	 * Discard the given Action card
	 * 
	 * @param action,
	 *            the Action to be discarded
	 */
	void discardAction(Action action);

	/**
	 * Gives the user a hint of what is going on
	 * 
	 * @param message,
	 *            the message to be printed.
	 * @param cards,
	 *            the Cards to be printed
	 */
	void hint(String message, Card... cards);

	/**
	 * Collect one more or fewer Noble from the queue this turn
	 * 
	 * @param positive,
	 *            a boolean, if true, collect more, else collect fewer
	 */
	void collectPlus(boolean positive);

	/**
	 * Acquires the hand of another Player. And calls that method for the other
	 * Player if that Player hasn't
	 * 
	 * @param p,
	 *            the Player whose hand this Player is going to acquire
	 */
	void acquireHand(Player p);

	/**
	 * Mark a specific field of this Player with some purpose
	 */
	void marked();

	/**
	 * Execute this Player's turn
	 */
	void turn();

}

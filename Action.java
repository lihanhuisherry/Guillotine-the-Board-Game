package Indy;

/**
 * The overall interface for Action cards in Guillotine
 */
public interface Action extends Card {

	/**
	 * Tests whether this Action Card has a legal target given the state of the
	 * current Guillotine Game
	 * 
	 * @param game,
	 *            the Guillotine game to test legality
	 * @return a boolean representing whether this card can be played at this
	 *         moment
	 */
	boolean hasLegalTarget(Guillotine game);

	/**
	 * Executes the actions this Action has upon played by the given Player
	 * 
	 * @param player,
	 *            the given Player
	 */
	void uponAction(Player player);

}

package Indy;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * The overall interface for the Guillotine game
 */
public interface Guillotine extends Comparator<Player> {

	/**
	 * Handles a special case that might arise because this game has a lot of
	 * stand-alone mechanics shared amongst few cards, so it makes sense to
	 * handle them separately
	 * 
	 * @param spec,
	 *            a SpcialCase, the special case specification
	 */
	void handleSpecialCase(SpecialCase spec);

	/**
	 * Marks the game ready to enter the next day so that as the turn of the
	 * current player ends, the game can enter the next day
	 */
	void markNextDay();

	/**
	 * Gets the discard pile of actions as an array of Action
	 * 
	 * @return an Action[] representing the discarded Actions
	 */
	Action[] getDiscarded();

	/**
	 * Gets the players in the game as an array of Player
	 * 
	 * @return an Player[] representing the players in this game
	 */
	Player[] getPlayers();

	/**
	 * Shuffles a given amount of cards at the front of the line of nobles (to
	 * be collected).
	 * 
	 * @param max,
	 *            an integer representing the number of Nobles to shuffle, max
	 *            == 0 implies shuffle all Nobles in the line
	 */
	void shuffle(int max);

	/**
	 * Adds the given Nobles into the line of nobles to be collected
	 * 
	 * @param added,
	 *            some Noble references to be added, null reference implies from
	 *            the Deck of Nobles
	 */
	void add(Noble... added);

	/**
	 * Adds the given Action card into the discard pile
	 * 
	 * @param toBeDiscarded,
	 *            an Action to be discarded
	 */
	void addDiscarded(Action toBeDiscarded);

	/**
	 * Draws a number of cards from the Action Deck to a List of Action
	 * 
	 * @param num,
	 *            the number of cards to draw from the Deck of Action
	 * 
	 * @return the list containing the cards drawn from the Action deck
	 */
	List<Action> draw(int amount);

	/**
	 * Removes the first Noble from the line to be collected
	 * 
	 * @return null if the line is empty, otherwise the removed first Noble
	 */
	Noble removeFirst();

	/**
	 * Gets the legal moves that a TargetAction can achieve as a Map from Noble
	 * to integer array, where each Noble is a legal target, and the integer
	 * array a set of spaces it can move. This method is used for the Target-
	 * Action to check whether it has any legal target, and store them if any
	 * 
	 * @param fixed,
	 *            a boolean representing whether the Action card specifies move
	 *            by a fixed amount
	 * @param color,
	 *            a Color representing the colors the Action card specifies, or
	 *            null if irrelevant
	 * @param extremum,
	 *            the extreme amount of spaces to move, could be negative if
	 *            it's a move backwards
	 * @return a Map<Noble, int[]> categorizing all legal moves
	 */
	Map<Noble, int[]> getSelectable(boolean fixed, Color color, int extremum);

	/**
	 * The specified Player requests a move of a specified Noble from a set
	 * specifying the range the player can choose from
	 * 
	 * @param player,
	 *            the specified player to make the move
	 * @param noble,
	 *            the noble to be moved
	 * @param moves,
	 *            the set of legal moves
	 */
	void move(Player player, Noble noble, int[] moves);

	/**
	 * Tests whether the Unpopular_Judge is in front of the line, in which case
	 * the player can't play any Action card
	 * 
	 * @return a boolean representing whether the Unpopular_Judge is there
	 */
	boolean isRushed();

	/**
	 * Displays the line of nobles for user to interact
	 */
	void displayQueue();

}

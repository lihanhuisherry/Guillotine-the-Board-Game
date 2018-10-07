package Indy;

/**
 * The overall interface for Noble cards
 */
public interface Noble extends Card {

	Color getColor();

	int getScore();

	Nobility getNobility();

	/**
	 * Executes the actions this Noble has upon collected by given Player
	 * 
	 * @param player,
	 *            the given Player
	 */
	void uponCollection(Player player);
}

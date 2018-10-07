package Indy;

/**
 * The most abstract implementation of Action cards
 */
public abstract class AbsAction implements Action {

	@Override
	public void uponAction(Player player) {
		boolean tripped = preAction(player);
		Guillotine game = player.getGame();
		game.handleSpecialCase(SpecialCase.TINKER_TAILOR_SOLDIER);
		if (tripped && !game.isRushed()) {
			game.displayQueue();
			player.selectAndPlay();
		}
	}

	@Override
	public boolean hasLegalTarget(Guillotine game) {
		return true;
	}

	/**
	 * Executes the actions before receiving the common actions of all Action
	 * 
	 * @param player,
	 *            the Player who plays this card
	 * @return whether playing this Action allows the Player to play another
	 */
	abstract protected boolean preAction(Player player);
}

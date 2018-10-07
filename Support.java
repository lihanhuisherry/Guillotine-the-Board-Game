package Indy;

/**
 * Another basic extension of AbsAction, a smaller subset that sits on the field
 * like Supports after played
 */
public abstract class Support extends AbsAction {

	@Override
	protected boolean preAction(Player player) {
		support(player);
		return false;
	}

	/**
	 * Executes the actions before receiving the common actions of all
	 * BaseAction
	 * 
	 * @param player,
	 *            the Player who plays this card
	 */
	protected abstract void support(Player player);
}
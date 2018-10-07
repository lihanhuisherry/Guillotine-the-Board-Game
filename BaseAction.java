package Indy;

/**
 * Basic extension of AbsAction, a smaller subset that doesn't sit on the field
 * like Supports after played
 */
public abstract class BaseAction extends AbsAction {

	@Override
	protected boolean preAction(Player player) {
		extraAction(player);
		player.getGame().addDiscarded(this);
		return false;
	}

	/**
	 * Executes the actions before receiving the common actions of all
	 * BaseAction
	 * 
	 * @param player,
	 *            the Player who plays this card
	 */
	abstract protected void extraAction(Player player);

}

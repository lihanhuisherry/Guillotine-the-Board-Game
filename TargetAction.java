package Indy;

import java.util.Map;

/**
 * Extension of BaseAction, a smaller subset that specifies likely multiple
 * targets on the field when cast
 */
public abstract class TargetAction extends BaseAction {

	private boolean fixed;
	private Color color;
	private int extremum;
	protected Map<Noble, int[]> choices;

	/**
	 * Constructs a TargetAction based on a series of parameters
	 * 
	 * @param fixed,
	 *            specifies whether the number of spaces move is fixed
	 * @param color,
	 *            specifies the Color required if non-null
	 * @param extremum,
	 *            the extreme amount of movement, could be negative for backward
	 */
	public TargetAction(boolean fixed, Color color, int extremum) {
		this.fixed = fixed;
		this.color = color;
		this.extremum = extremum;
	}

	@Override
	protected void extraAction(Player player) {
		Noble selected = player.selectNoble(choices.keySet().toArray(new Noble[0]));
		player.getGame().move(player, selected, choices.get(selected));
	}

	@Override
	public boolean hasLegalTarget(Guillotine game) {
		this.choices = game.getSelectable(fixed, color, extremum);
		return !this.choices.isEmpty();
	}

}

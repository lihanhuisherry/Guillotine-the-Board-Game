package Indy;

/*
 * This class models an AI for this game, 
 * which utilizes a computational algorithm called RSA.
 */
public class GuillotineAI extends AbPlayer {

	private static int index = 0;

	public GuillotineAI(Guillotine game) {
		super(game, String.format("GuillotineAI #%d", index++));
	}

	@Override
	public Player selectPlayer(boolean opponent) {
		Player[] players = game.getPlayers();
		return players[0] != this ? players[0] : players[1];
	}

	@Override
	public Action selectAction(Player player) {
		Action[] actions = player == null ? getGame().getDiscarded() : player.getActions(false);
		return actions.length > 0 ? actions[0] : null;
	}

	@Override
	public Noble selectNoble(Noble[] nobles) {
		for (Noble n : nobles) {
			return n;
		}
		return null;
	}

	@Override
	public Global selectGlobal(Player p) {
		for (Global g : p.getGlobals()) {
			return g;
		}
		return null;
	}

	@Override
	public void hint(String message, Card... cards) {
		// Do nothing
	}

	@Override
	public int selectInt(int[] moves) {
		return moves[moves.length - 1];
	}

	@Override
	public void selectAndPlay() {
		Action[] selectFrom = getActions(true);
		if (selectFrom.length > 0) {
			hand.remove(selectFrom[0]);
			selectFrom[0].uponAction(this);
		}

	}
}

package Indy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * An abstract implementation of the Guillotine game logic. This abstract class
 * exists for future extensions via different platforms, maybe say JavaScript
 */
public abstract class Game implements Guillotine {

	protected int twice_day;
	protected Deck<Action> actions;
	protected Deck<Noble> nobles;
	protected LinkedList<Player> players;
	protected ArrayList<Action> discarded;
	protected LinkedList<Noble> queue;

	/**
	 * Constructs a game
	 */
	public Game() {
		this.twice_day = 0;
		this.actions = new Deck<Action>(Constants.WORKSPACE, Constants.ACTION_FOLDERS);
		this.nobles = new Deck<Noble>(Constants.WORKSPACE, Constants.NOBLE_FOLDERS);
		this.discarded = new ArrayList<Action>();
		this.queue = new LinkedList<Noble>();
		this.players = new LinkedList<Player>();
	}

	@Override
	public void markNextDay() {
		this.twice_day++;
	}

	@Override
	public Action[] getDiscarded() {
		return discarded.toArray(new Action[0]);
	}

	@Override
	public Player[] getPlayers() {
		return players.toArray(new Player[0]);
	}

	@Override
	public void addDiscarded(Action toBeDiscarded) {
		discarded.add(toBeDiscarded);
	}

	@Override
	public ArrayList<Action> draw(int amount) {
		return actions.deal(amount);
	}

	@Override
	public Noble removeFirst() {
		return queue.isEmpty() ? null : queue.removeFirst();
	}

	@Override
	public void add(Noble... added) {
		if (added.length == 0) {
			nobles.add(-1, queue);
		} else {
			for (Noble n : added) {
				if (n == null)
					nobles.add(1, queue);
				else
					queue.addLast(n);
			}
		}
	}

	@Override
	public void shuffle(int max) {
		Noble[] arr = new Noble[0];
		if (max <= 0 || max >= queue.size()) {
			arr = queue.toArray(arr);
			queue.clear();
		} else {
			arr = new Noble[max];
			for (int i = 0; i < max; i++) {
				arr[i] = queue.removeFirst();
			}
		}
		Deck<Noble> temp = new Deck<Noble>(arr);
		temp.add(-arr.length, queue);
	}

	@Override
	public void handleSpecialCase(SpecialCase spec) {
		switch (spec) {
		case LET_THEM_EAT_CAKE:
			Noble marie = null;
			for (Noble noble : queue) {
				if (noble.getNobility() == Nobility.ROYALTY && noble != queue.getFirst()) {
					marie = noble;
					break;
				}
			}
			if (marie != null) {
				queue.remove(marie);
				queue.addFirst(marie);
			}
			break;
		case SPOTLIGHT:
			Noble faithless = null;
			for (Noble noble : queue) {
				if (noble.getColor() == Color.BLUE && noble != queue.getFirst()) {
					faithless = noble;
					break;
				}
			}
			if (faithless != null) {
				queue.remove(faithless);
				queue.addFirst(faithless);
			}
			break;
		case THE_LONG_MARCH:
			LinkedList<Noble> temp = queue;
			this.queue = new LinkedList<Noble>();
			for (Noble noble : temp) {
				queue.addFirst(noble);
			}
			break;
		case PRIDE:
			for (Player p : players) {
				if (p != players.getFirst()) {
					p.discardAction(null);
				}
			}
			break;
		case TINKER_TAILOR_SOLDIER:
			Noble spy = null;
			for (Noble noble : queue) {
				if (noble.getNobility() == Nobility.STEALTH && noble != queue.getLast()) {
					spy = noble;
					break;
				}
			}
			if (spy != null) {
				queue.remove(spy);
				queue.addLast(spy);
			}
			break;
		case LATE_ARRIVAL:
			nobles.add(-3, queue);
			break;
		default:
			System.err.printf("Special case %s not yet implemented.\n", spec);
		}
	}

	@Override
	public boolean isRushed() {
		return queue.getFirst().getNobility() == Nobility.NOTORIETY;
	}

	@Override
	public void move(Player player, Noble noble, int[] moves) {
		int index = queue.indexOf(noble);
		queue.remove(noble);
		if (moves.length == 0)
			queue.addFirst(noble);
		else if (moves.length == 1)
			queue.add(index - moves[0], noble);
		else
			queue.add(index - player.selectInt(moves), noble);
	}

	@Override
	public Map<Noble, int[]> getSelectable(boolean fixed, Color color, int extremum) {
		int i = 0;
		int length = queue.size();
		HashMap<Noble, int[]> map = new HashMap<Noble, int[]>();
		for (Noble noble : queue) {
			int[] arr = eligibleRange(fixed, color, extremum, i++, noble, length);
			if (arr != null) {
				map.put(noble, arr);
			}
		}
		return map;
	}

	/**
	 * Finds the eligible range a Noble can move in the queue given the following
	 * parameters and two special cases: 1) !fixed && extremum == 0 => every Noble
	 * is eligible; 2) fixed && extremum == 0 => Palace Guards and Palace Guards
	 * only are eligible.
	 *
	 * @param fixed,
	 *            a boolean denoting whether this is a fixed move
	 * @param color,
	 *            a Color that eligible nobles have to match if non null
	 * @param extremum,
	 *            the extreme amount of spaces to move, could be negative
	 * @param index,
	 *            the current index of the noble
	 * @param noble,
	 *            a reference to that noble
	 * @param length,
	 *            the length of the current queue
	 * @return an integer array denoting the possible numbers of spaces to move
	 *         forward, with negative results denoting backward moves and empty
	 *         arrays denoting moves to the front of the queue
	 */
	private int[] eligibleRange(boolean fixed, Color color, int extremum, int index, Noble noble, int length) {
		if (extremum == 0) {
			return fixed && noble.getNobility() != Nobility.VIGILANCE ? null : new int[0];
		} else if (color == null || noble.getColor() == color) {
			int dest = index - extremum;
			if (fixed) {
				return (dest < 0 || length <= dest) ? null : new int[] { extremum };
			} else {
				boolean forward = extremum > 0;
				int altDest = index + (forward ? -1 : 1);
				int max = forward ? altDest : dest;
				int min = forward ? dest : altDest;
				while (min < 0)
					min++;
				while (max >= length)
					max--;
				int arrlen = max - min + 1;
				if (arrlen > 0) {
					index -= max;
					int[] res = new int[arrlen];
					for (int i = 0; i < arrlen; i++) {
						res[i] = index++;
					}
					return res;
				}
			}
		}
		return null;
	}

	/**
	 * Advance the game to the nextDay(), also used to start the game, which calls
	 * nextDay() recursively as long as the game hasn't ended
	 */
	protected void nextDay() {
		while (twice_day < 4) {
			nobles.add(Constants.STANDARD_QUEUE_SIZE, queue);
			while ((twice_day % 2 == 0) && !queue.isEmpty()) {
				displayQueue();
				Player actor = players.removeFirst();
				players.addLast(actor);
				actor.turn();
			}
			twice_day &= ~1;
			twice_day += 2;
			this.queue.clear();
		}
		gameOver();
	}

	@Override
	public int compare(Player o1, Player o2) {
		return -Integer.compare(o1.getScore(), o2.getScore());
	}

	/**
	 * Ends the game, an interface for subplatforms
	 */
	protected abstract void gameOver();
}

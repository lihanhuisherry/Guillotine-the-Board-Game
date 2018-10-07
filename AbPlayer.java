package Indy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * An abstract implementation of the Player interface. This abstract class
 * exists for future extensions via different platforms, maybe say JavaScript
 */
public abstract class AbPlayer implements Player {

	private final String name;
	protected final Guillotine game;
	protected ArrayList<Action> hand;
	protected HashMap<Color, ArrayList<Noble>> collected;
	protected HashMap<Global, Support> globals;
	private Noble cashe;
	private int collect_num;
	private boolean marked;

	/**
	 * Constructs a AbPlayer based on the Guillotine game it's in and a name
	 * 
	 * @param game,
	 *            the Guillotine game this AbPlayer is in
	 * 
	 * @param name,
	 *            the name of this Player
	 */
	public AbPlayer(Guillotine game, String name) {
		this.game = game;
		this.name = name;
		this.collected = new HashMap<Color, ArrayList<Noble>>();
		Arrays.stream(Color.values()).forEach(color -> collected.put(color, new ArrayList<Noble>()));
		this.hand = new ArrayList<Action>();
		this.globals = new HashMap<Global, Support>();
		this.collect_num = 1;
		this.marked = false;
		hand.addAll(game.draw(Constants.INITIAL_HAND_SIZE));
	}

	@Override
	public void turn() {
		hint(", it's your turn now");
		if (!(game.isRushed() || marked)) {
			selectAndPlay();
		}
		while (collect_num > 0) {
			collect();
			collect_num--;
		}
		hand.addAll(game.draw(1));
		collect_num = 1;
		marked = false;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Action[] getActions(boolean toPlay) {
		ArrayList<Action> legal;
		if (toPlay) {
			legal = new ArrayList<Action>();
			for (Action card : hand) {
				if (card.hasLegalTarget(game)) {
					legal.add(card);
				}
			}
		} else {
			legal = hand;
		}
		return legal.toArray(new Action[0]);
	}

	@Override
	public Global[] getGlobals() {
		return globals.keySet().toArray(new Global[0]);
	}

	@Override
	public Support[] getSupports() {
		return globals.values().toArray(new Support[0]);
	}

	@Override
	public Noble[] getCollected() {
		ArrayList<Noble> list = new ArrayList<Noble>();
		collected.values().forEach(list::addAll);
		return list.toArray(new Noble[0]);
	}

	@Override
	public void addGlobal(Support support, Global g) {
		globals.put(g, support);
	}

	@Override
	public void discardGlobal(Global g) {
		globals.remove(g);
	}

	@Override
	public int getScore() {
		boolean indifferent = globals.containsKey(Global.INDIFFERENT_PUBLIC);
		boolean tragic = false;
		boolean waiting = false;
		int pgcount = 0;
		int score = 0;
		for (Noble noble : getCollected()) {
			if (!(noble.getColor() == Color.GREY && indifferent)) {
				score += noble.getScore();
			}
			switch (noble.getNobility()) {
			case VIGILANCE:
				pgcount++;
				break;
			case COMPANIONSHIP:
				if (waiting)
					score += Constants.COMPANIONSHIP_BONUS;
				else
					waiting = true;
				break;
			case TRAGEDY:
				tragic = true;
				break;
			default:
				// Do nothing
			}
		}
		if (globals.containsKey(Global.CIVIC_SUPPORT)) {
			score += collected.get(Color.GREEN).size();
		}
		if (globals.containsKey(Global.MILITARY_SUPPORT)) {
			score += collected.get(Color.RED).size();
		}
		if (globals.containsKey(Global.CHURCH_SUPPORT)) {
			score += collected.get(Color.BLUE).size();
		}
		if (globals.containsKey(Global.FOUNTAIN_OF_BLOOD)) {
			score += Constants.GLOBAL_SCORE_DIFF;
		}
		if (globals.containsKey(Global.TOUGH_CROWD)) {
			score -= Constants.GLOBAL_SCORE_DIFF;
		}
		if (indifferent) {
			score += collected.get(Color.GREY).size();
		} else if (tragic) {
			score -= collected.get(Color.GREY).size();
		}
		return score + pgcount * pgcount;
	}

	@Override
	public Guillotine getGame() {
		return game;
	}

	@Override
	public void draw(Action... actions) {
		for (Action card : actions) {
			if (Constants.MAXIMUM_HAND_SIZE <= hand.size()) {
				break;
			} else if (card == null) {
				hand.addAll(game.draw(1));
			} else {
				hand.add(card);
			}
		}
	}

	@Override
	public void collect() {
		Noble toBeCollected = game.removeFirst();
		if (toBeCollected != null) {
			getNoble(toBeCollected);
			toBeCollected.uponCollection(this);
			if (globals.containsKey(Global.FOREIGN_SUPPORT) && toBeCollected.getColor() == Color.PURPLE) {
				hand.addAll(game.draw(1));
			}
			hint(", you just collected this noble.", toBeCollected);
		}
	}

	@Override
	public void getNoble(Noble noble) {
		this.cashe = noble;
		collected.get(noble.getColor()).add(noble);
	}

	@Override
	public Noble discardNoble(boolean random) {
		Noble temp = cashe;
		if (temp != null) {
			if (random) {
				// For Missing Heads
				Noble[] dah = getCollected();
				temp = dah[(int) (Math.random() * dah.length)];
			} else {
				// For Missed!
				this.cashe = null;
			}
			collected.get(temp.getColor()).remove(temp);
		}
		return temp;
	}

	@Override
	public void collectPlus(boolean positive) {
		if (positive)
			collect_num++;
		else
			collect_num--;
	}

	@Override
	public void acquireHand(Player p) {
		ArrayList<Action> temp = new ArrayList<Action>();
		for (Action act : p.getActions(false)) {
			temp.add(act);
		}
		if (marked) {
			marked = false;
		} else {
			p.marked();
			p.acquireHand(this);
		}
		this.hand = temp;
	}

	@Override
	public void discardAction(Action action) {
		if (action == null) {
			int size = hand.size();
			if (size > 0) {
				action = hand.remove((int) (Math.random() * hand.size()));
			}
		} else {
			hand.remove(action);
		}
		game.addDiscarded(action);
	}

	@Override
	public void marked() {
		this.marked = true;
	}
}

package Indy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class Deck<T extends Card> {

	private int current;
	private T[] cards;

	/**
	 * Constructs a deck from an array of cards
	 *
	 * @param cards,
	 *            an array of cards
	 */
	public Deck(T[] cards) {
		this.cards = cards;
		this.current = cards.length;
	}

	/**
	 * Constructs a deck from some strings that point to a bunch of files that
	 * contain the names of the cards to be added to the deck. The cards are
	 * made through java.lang.reflect
	 *
	 * @param common,
	 *            a String that represents the common parts of the paths
	 * @param names,
	 *            the names of each of the files
	 */
	@SuppressWarnings("unchecked")
	public Deck(String common, String[] names) {
		ArrayList<T> builder = new ArrayList<T>();
		for (String name : names) {
			String path = common + name + "txt";
			try {
				for (String className : Files.readAllLines(Paths.get(path))) {
					if (className.length() == 0) {
						continue;
					}
					char c = className.charAt(0);
					if (c < 'A' || c > 'Z') {
						// It's just a line of comments
						continue;
					} else if (Arrays.asList(className.split("\\W+")).contains("implemented")) {
						// The rest of the file is not implemented
						break;
					}
					try {
						className = name.replace("/", ".") + className;
						builder.add((T) Class.forName(className).newInstance());
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
						System.err.println("failed to create card with name: " + className);
					}
				}
			} catch (IOException e) {
				System.err.println("failed to access file with name: " + path);
			}
		}
		this.current = builder.size();
		this.cards = builder.toArray((T[]) new Card[0]);
	}

	/**
	 * Decrement the current position, and swaps the card at the new current
	 * position with the one at the target position and returns the target
	 * position's original value
	 *
	 * @param target,
	 *            the target position to be swapped at
	 * @return the card at the current position in the end
	 */
	private T next() {
		int target = (int) (Math.random() * current);
		T temp = cards[target];
		current--;
		cards[target] = cards[current];
		return cards[current] = temp;
	}

	/**
	 * Shuffles back a collection of cards to form a new deck
	 *
	 * @param putBack,
	 *            a collection of cards to be shuffled back
	 *
	 * @return a new Deck
	 * @Deprecated because this can mess things up
	 */
	@Deprecated
	public Deck<T> shuffleBack(Collection<T> putBack) {
		T[] newArray = Arrays.copyOf(cards, current += putBack.size());
		for (T card : putBack) {
			current--;
			newArray[current] = card;
		}
		return new Deck<T>(newArray);
	}

	/**
	 * return the deck to its original state, with all the cards back
	 */
	public void shuffleBack() {
		current = cards.length;
	}

	/**
	 * Deals an amount of cards and store them in the Collection provided
	 *
	 * @param amount,
	 *            the amount to deal
	 */
	public ArrayList<T> deal(int amount) {
		ArrayList<T> list = new ArrayList<T>();
		for (; amount > 0 && current > 0; amount--) {
			list.add(next());
		}
		return list;
	}

	/**
	 * Adds an amount of cards and store them in the LinkedList provided
	 *
	 * @param amount,
	 *            |amount| is the amount of cards to be added, negative == front
	 * @param list,
	 *            the provided LinkedList to store the added cards
	 */
	public void add(int amount, LinkedList<T> list) {
		if (amount > 0)
			for (; amount > 0 && current > 0; amount--) {
				list.addLast(next());
			}
		else
			for (; amount < 0 && current > 0; amount++) {
				list.addFirst(next());
			}
	}

}

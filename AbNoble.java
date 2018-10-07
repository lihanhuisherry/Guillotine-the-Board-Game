package Indy;

/**
 * A basic abstract implementation of the Noble class
 */
public abstract class AbNoble implements Noble {

	private int score;
	private Color color;

	/**
	 * Constructs a AbNoble with its score and Color
	 * 
	 * @param score,
	 *            the score of the noble
	 * @param color,
	 *            the Color of this AbNoble
	 */
	public AbNoble(int score, Color color) {
		this.score = score;
		this.color = color;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public Nobility getNobility() {
		return Nobility.NORMALCY;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}

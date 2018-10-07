package Indy;

import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * This class is generated whenever a new action/noble card is generated.
 *
 * @param <T> the type of Card to display
 */
public class JavaFXCard<T extends Card> {
	private ImageView _view;
	private ImageView _enlarge;
	private Pane _root;
	private T _card;
	private boolean _enabled = true;

	/**
	 * Constructor used when constructing a javafx card with a png source file.
	 * 
	 * @param card
	 * @param root
	 */
	public JavaFXCard(T card, Pane root) {
		this(card, root, ".png");
	}

	/**
	 * Constructor used when constructing a noble/action cards
	 * 
	 * @param card
	 * @param root
	 * @param format
	 */
	public JavaFXCard(T card, Pane root, String format) {
		this._card = card;
		String fullPath = card.getClass().getName().replaceAll("\\.", "/") + format;
		_view = new ImageView(new Image(ClassLoader.getSystemResourceAsStream(fullPath)));
		// if card is noble, view set fitSize can be different from set set fit for
		// actions
		_view.setFitHeight(Constants.VIEW_HEIGHT);
		_view.setFitWidth(Constants.VIEW_WIDTH);
		_enlarge = new ImageView(new Image(ClassLoader.getSystemResourceAsStream(fullPath)));
		_enlarge.setFitHeight(Constants.VIEW_HEIGHT * 1.5);
		_enlarge.setFitWidth(Constants.VIEW_WIDTH * 1.5);
		_enlarge.relocate(Constants.ENLARGE_X, Constants.ENLARGE_Y);
		this._root = root;
		this.setOnAction();
		root.getChildren().add(_view);
	}

	/**
	 * Images on the board will enlarge when the mouse hovers over them
	 */
	private void setOnAction() {
		_view.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				_view.setEffect(new Glow(0.3));
				if (_enabled) {
					_root.getChildren().add(_enlarge);
				}
			}
		});

		_view.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				_view.setEffect(new Glow(-0.3));
				if (_enabled) {
					removeEnlarge();
				}
			}
		});
	}

	/**
	 * relocate the Noble cards before adding them to the game pane
	 * 
	 * @param i
	 */
	public void setNoblePosition(int i) {
		double x = 0.0;
		double y = 0.0;
		if (i <= 5) {
			x = Constants.VIEW_X + i * (20 + Constants.VIEW_WIDTH);
			y = Constants.VIEW_Y;
		} else {
			x = Constants.VIEW_X + (i - 6) * (20 + Constants.VIEW_WIDTH);
			y = Constants.VIEW_Y + Constants.VIEW_HEIGHT + 20;
		}
		_view.relocate(x, y);
	}

	/**
	 * relocate the Action cards before adding them to the game pane
	 * 
	 * @param i
	 */
	public void setActionPosition(int i) {
		double x = 0.0;
		double y = 0.0;
		x = Constants.ACTION_X + i * (Constants.VIEW_ADJUSTMENT * Constants.VIEW_WIDTH);
		y = Constants.ACTION_Y;
		_view.relocate(x, y);
	}

	/**
	 * relocate the cards that can be inspected before adding them to the game pane
	 * 
	 * @param i
	 */
	public void setInspectedPosition(int i) {
		this._enabled = false;
		double x = 0.0;
		double y = 0.0;
		if (i <= 5) {
			x = Constants.INSPECT_X + i * (20 + Constants.VIEW_WIDTH);
			y = Constants.INSPECT_Y;
		} else if (i <= 11) {
			x = Constants.INSPECT_X + (i - 6) * (20 + Constants.VIEW_WIDTH);
			y = Constants.INSPECT_Y + Constants.VIEW_HEIGHT + 20;
		} else {
			x = Constants.INSPECT_X + (i - 12) * (20 + Constants.VIEW_WIDTH);
			y = Constants.INSPECT_Y + 2 * Constants.VIEW_HEIGHT + 40;
		}
		_view.relocate(x, y);
	}

	/**
	 * Return the image of a certain card
	 * 
	 * @return
	 */
	public ImageView getView() {
		return this._view;
	}

	/**
	 * Remove the enlarged copy of a card after the mouse exits the card
	 */
	public void removeEnlarge() {
		_root.getChildren().remove(_enlarge);
	}

	/**
	 * Return the card passed in through the constructor
	 * 
	 * @return
	 */
	public T getCard() {
		return this._card;
	}
}

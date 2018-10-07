package Indy;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * A concrete subclass of the Guillotine prompt that is used to display Cards
 *
 * @param <T>
 *            the type of Cards this class of prompts display
 */
public class Prompt<T extends Card> extends GuillotinePrompt<T> {

	private ArrayList<JavaFXCard<T>> kept;
	private ArrayList<JavaFXCard<? extends Card>> floating;

	/**
	 * Constructs a Prompt based on some parameters, just like GuillotinePrompt
	 * 
	 * @param game,
	 *            the JavaFXGame the Prompt is used for
	 * @param title,
	 *            the title of this Prompt
	 * @param stuff,
	 *            the stuff to be displayed inside this prompt.
	 */
	public Prompt(JavaFXGame game, String title, T[] cards) {
		super(game, title, cards);
	}

	@Override
	protected void initPane(T[] cards) {
		int i = 0, j = 3;
		this.floating = new ArrayList<JavaFXCard<? extends Card>>();
		this.kept = new ArrayList<JavaFXCard<T>>();
		for (T each : cards) {
			JavaFXCard<T> fxCard = new JavaFXCard<T>(each, _pane);
			fxCard.setActionPosition(i++);
			kept.add(fxCard);
			fxCard.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent t) {
					fxCard.removeEnlarge();
					result = fxCard.getCard();
					cleanUp();
				}
			});
		}
		Button forFeit = new Button("Skip");
		tools.add(forFeit);
		_pane.getChildren().add(forFeit);
		forFeit.setFocusTraversable(false);
		forFeit.relocate(FACTOR_X, FACTOR_Y * j++);
		forFeit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				cleanUp();
			}
		});
		Button graveyard = new Button("Discard Pile");
		tools.add(graveyard);
		_pane.getChildren().add(graveyard);
		graveyard.setFocusTraversable(false);
		graveyard.relocate(FACTOR_X, (FACTOR_Y * j++));
		graveyard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				for (JavaFXCard<? extends Card> fxCard : floating) {
					_floater.getChildren().remove(fxCard.getView());
				}
				floating.clear();
				int k = 0;
				for (Action discard : game.getDiscarded()) {
					JavaFXCard<Action> fxDiscard = new JavaFXCard<Action>(discard, _floater);
					fxDiscard.setInspectedPosition(k++);
					floating.add(fxDiscard);
				}
			}
		});

		for (Player player : game.getPlayers()) {
			Button inspect = new Button(String.format("%s's Score: (%d)", player, player.getScore()));
			tools.add(inspect);
			_pane.getChildren().add(inspect);
			inspect.setFocusTraversable(false);
			inspect.relocate(FACTOR_X, (FACTOR_Y * j++));
			inspect.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					for (JavaFXCard<? extends Card> fxCard : floating) {
						_floater.getChildren().remove(fxCard.getView());
					}
					floating.clear();
					int k = 0;
					for (Noble noble : player.getCollected()) {
						JavaFXCard<Noble> fxNoble = new JavaFXCard<Noble>(noble, _floater);
						fxNoble.setInspectedPosition(k++);
						floating.add(fxNoble);
					}
					for (Action support : player.getSupports()) {
						JavaFXCard<Action> fxSupport = new JavaFXCard<Action>(support, _floater);
						fxSupport.setInspectedPosition(k++);
						floating.add(fxSupport);
					}
				}
			});
		}
	}

	/**
	 * Cleans up the Nodes generated in this class and displayed on the main pane
	 */
	private void cleanUp() {
		for (JavaFXCard<T> card : kept) {
			_pane.getChildren().remove(card.getView());
		}
		_pane.getChildren().removeAll(tools);
		hide();
	}

	public T getOrDefault(T dephault) {
		return result == null ? dephault : result;
	}
}

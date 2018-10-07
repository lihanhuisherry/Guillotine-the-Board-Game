package Indy;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * UI design for the game board
 */
public class PaneOrganizer {

	private Pane _root;
	private Pane _upRoot;
	private Pane _downRoot;
	private Button _exit;
	private final Stage _main;
	private Button _start;
	private Button _restart;
	private Button _gameOver;
	private ArrayList<Label> _labels;
	private ArrayList<JavaFXCard<Noble>> _displayStored;
	private String[] _names;
	private Thread _game;

	/**
	 * Constructor for paneOrganizer
	 *
	 * @param main, the passed-in stage on which paneOrganizer's instance will show up
	 * @param names, the names entered by the players
	 */
	public PaneOrganizer(Stage main, ArrayList<String> names) {
		this._main = main;
		this._labels = new ArrayList<Label>();
		this._names = names.toArray(new String[0]);
		this.setUpPanes();
		this.setUpButtons();
		this._displayStored = new ArrayList<JavaFXCard<Noble>>();
		ImageView view = new ImageView(new Image(getClass().getResourceAsStream("GameBoard.png")));
		view.setFitWidth(Constants.USERSCREEN_WIDTH);
		view.setFitHeight(Constants.USERSCREEN_HEIGHT);
		_root.getChildren().addAll(view, _upRoot, _downRoot);
	}

	/**
	 * Update the noble on board after each turn
	 *
	 * @param updateNobles
	 */
	public void updateNobles(Iterable<Noble> updateNobles) {
		for (JavaFXCard<Noble> noble : _displayStored) {
			_upRoot.getChildren().remove(noble.getView());
		}
		_displayStored.clear();
		int i = 0;
		for (Noble noble : updateNobles) {
			JavaFXCard<Noble> fxCard = new JavaFXCard<Noble>(noble, _upRoot);
			_displayStored.add(fxCard);
			fxCard.setNoblePosition(i++);
		}
	}

	/**
	 * set the button glows of buttons on this page
	 *
	 * @param button
	 */
	private void setButtonGlow(Button button) {
		button.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				button.setEffect(new Glow(0.3));
			}
		});

		button.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				button.setEffect(new Glow(-0.3));
			}
		});
	}

	/**
	 * private helper method sets up panes
	 */
	private void setUpPanes() {
		_root = new Pane();
		_root.setPrefSize(Constants.USERSCREEN_WIDTH, Constants.USERSCREEN_HEIGHT);
		_upRoot = new Pane();
		_upRoot.setPrefHeight(Constants.UPROOT_HEIGHT);
		_upRoot.relocate(Constants.UPROOT_X, Constants.UPROOT_Y);
		_downRoot = new Pane();
		_downRoot.setPrefHeight(Constants.DOWNROOT_HEIGHT);
		_downRoot.relocate(0, Constants.UPROOT_HEIGHT);
	}

	/**
	 * private helper method sets up buttons
	 */
	private void setUpButtons() {
		_exit = new Button();
		_exit.setStyle("-fx-background-color: transparent");
		this.setButtonGlow(_exit);
		_exit.setGraphic(
				new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/exit.png"))));
		_exit.relocate(Constants.QUIT_X, Constants.QUIT_Y);
		_exit.setFocusTraversable(false);
		_exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		_start = new Button();
		_start.setStyle("-fx-background-color: transparent");
		_start.setGraphic(
				new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/start.png"))));
		_start.setFocusTraversable(false);
		this.setButtonGlow(_start);
		_start.relocate(Constants.START_X, Constants.START_Y);
		_start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PaneOrganizer.this._downRoot.getChildren().remove(_start);
				_root.getChildren().remove(3);
				PaneOrganizer.this._game = new Thread(new JavaFXGame(PaneOrganizer.this, _names));
				_downRoot.getChildren().add(_restart);
				_game.run();
			}
		});

		_restart = new Button();
		_restart.setStyle("-fx-background-color: transparent");
		this.setButtonGlow(_restart);
		_restart.setGraphic(
				new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/restart.png"))));
		_restart.relocate(Constants.START_X, Constants.START_Y);
		_restart.setFocusTraversable(false);
		_restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				_root.getChildren().remove(_gameOver);
				_root.getChildren().removeAll(_labels);
				_labels.clear();
				if (_game != null) {
					_game.interrupt();
					_game = new Thread(new JavaFXGame(PaneOrganizer.this, _names));
					_game.run();
				}
			}
		});

		_downRoot.getChildren().addAll(_exit, _start);
	}

	/**
	 * notify the players that the game is over
	 *
	 * @param messages, the winner's information
	 */
	public void gameOver(String[] messages) {
		_gameOver = new Button();
		_gameOver.setStyle("-fx-background-color: transparent");
		_gameOver.setGraphic(
				new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__PaneOrganizer/gameover.png"))));
		_gameOver.relocate(Constants.GAMEOVER_X, Constants.GAMEOVER_Y);
		_gameOver.setFocusTraversable(false);
		int i = 6;
		for (String message : messages) {
			Label label = new Label(message);
			label.relocate(Constants.GAMEOVER_X + 120, Constants.GAMEOVER_Y + 30 * i++);
			label.setFont(new Font("Arial", 36));
			_labels.add(label);
		}
		_root.getChildren().addAll(_labels);
		_root.getChildren().add(_gameOver);
	}

	public Pane getRoot() {
		return _root;
	}

	public Pane getDisp() {
		return _downRoot;
	}

	public Stage getMain() {
		return _main;
	}
}

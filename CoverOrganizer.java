package Indy;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * UI design of the game's cover page
 */
public class CoverOrganizer {

	private Button _newGame;
	private Button _exit;
	private Button _tutorials;
	private Button _start;
	private Button _back;
	private Button _input;
	private Button _anotherBack;
	private Pane _coverPane;
	private Stage _stage;
	private ImageView _instruction;
	private TextField _player1 = new TextField();
	private TextField _player2 = new TextField();
	private TextField _player3 = new TextField();
	private TextField _player4 = new TextField();
	private TextField _player5 = new TextField();
	private TextField[] _players;

	/**
	 * This class models the cover for the game
	 *
	 * @param stage, stage where the coverOrganizer instance holds
	 */
	public CoverOrganizer(Stage stage) {
		this._players = new TextField[5];
		for (int i = 0; i < 5; i++) {
			 _players[i] = new TextField();
		}
		_player1 = _players[0];
		_player2 = _players[1];
		_player3 = _players[2];
		_player4 = _players[3];
		_player5 = _players[4];
		ImageView view = new ImageView(new Image(getClass().getResourceAsStream("cover.png")));
		view.setFitWidth(Constants.USERSCREEN_WIDTH);
		view.setFitHeight(Constants.USERSCREEN_HEIGHT);
		this.setButton();
		this.setButtonReaction();
		this._coverPane = new Pane();
		this._coverPane.getChildren().addAll(view, _newGame, _exit, _tutorials);
		this._stage = stage;
	}

	/**
	 * private helper method that sets up textField
	 */
	private void setText() {
		GridPane rootNode = new GridPane();
		rootNode.setPadding(new Insets(15));
		rootNode.setHgap(5);
		rootNode.setVgap(5);
		rootNode.setAlignment(Pos.CENTER);

		this.setTextField(rootNode, _player1, "First Player's Name:", 0);
		this.setTextField(rootNode, _player2, "Second Player's Name:", 1);
		this.setTextField(rootNode, _player3, "Third Player's Name:", 2);
		this.setTextField(rootNode, _player4, "Fourth Player's Name:", 3);
		this.setTextField(rootNode, _player5, "Fifth Player's Name:", 4);
		rootNode.relocate(Constants.ROOTNODE_X, Constants.ROOTNODE_Y);
		_coverPane.getChildren().add(rootNode);
	}

	/**
	 * private helper method that sets up textField
	 */
	private void setTextField(GridPane rootNode, TextField player, String string, int i) {
		Label label = new Label(string);
		label.setStyle("-fx-text-fill: #d93434;");
		label.setEffect(new Glow(0.6));
		rootNode.add(label, 0, i);
		rootNode.add(player, 1, i);
	}

	/**
	 * private helper method that sets up buttons
	 */
	private void setButton() {
		_newGame = new Button();
		this.newButton(_newGame, Constants.NEWGAME_X, Constants.NEWGAME_Y);
		_exit = new Button();
		this.newButton(_exit, Constants.EXIT_X, Constants.EXIT_Y);
		_tutorials = new Button();
		this.newButton(_tutorials, Constants.TUTORIALS_X, Constants.TUTORIALS_Y);
		_start = new Button();
		this.newButton(_start, Constants.EXIT_X, Constants.EXIT_Y);
		_back = new Button();
		this.newButton(_back, Constants.BACK_X, Constants.BACK_Y);
		_anotherBack = new Button();
		this.newButton(_anotherBack, Constants.ANOTHERBACK_X, Constants.ANOTHERBACK_Y);
		_input = new Button();
		this.newButton(_input, Constants.INPUT_X, Constants.INPUT_Y);
		_start.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/start.png"))));
		_exit.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/exit.png"))));
		_tutorials.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/tutorial.png"))));
		_newGame.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/newgame.png"))));
		_back.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/back.png"))));
		_anotherBack.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/back.png"))));
		ImageView input = new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/input.png")));
		input.setFitHeight(Constants.INPUT_HEIGHT);
		input.setFitWidth(Constants.INPUT_WIDTH);
		_input.setGraphic(input);
	}

	/**
	 * private helper method that sets up button glow effect
	 */
	private void setButtonGlow(Button button) {
		button.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				button.setEffect(new Glow(0.5));
			}
		});

		button.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				button.setEffect(new Glow(-0.5));
			}
		});
	}

	/**
	 * set the basic property of passed-in buttons
	 */
	private void newButton(Button button, double x, double y) {
		button.setStyle("-fx-background-color: transparent");
		button.relocate(x, y);
		button.setFocusTraversable(false);
		this.setButtonGlow(button);
	}

	/**
	 * set up button functions
	 */
	private void setButtonReaction() {
		_exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		_tutorials.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				_instruction = new ImageView(new Image(getClass().getResourceAsStream("instruction.png")));
				_coverPane.getChildren().removeAll(_newGame, _tutorials, _exit);
				_instruction.setFitHeight(Constants.INSTRUCTION_HEIGHT);
				_instruction.setFitWidth(Constants.INSTRUCTION_WIDTH);
				_instruction.relocate(Constants.USERSCREEN_WIDTH/2 - 260, 1.8*Constants.USERSCREEN_HEIGHT/5);
				_coverPane.getChildren().addAll(_instruction, _anotherBack);
			}
		});

		_newGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				_coverPane.getChildren().removeAll(_newGame, _tutorials, _exit);
				CoverOrganizer.this.setText();
				_coverPane.getChildren().addAll(_input, _start, _back);
			}
		});

		_anotherBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				_coverPane.getChildren().removeAll(_instruction, _anotherBack);
				_coverPane.getChildren().addAll(_newGame, _tutorials, _exit);
			}
		});

		_start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<String> temp = new ArrayList<String>();
				for (TextField field : _players) {
					String name = field.getText();
					if (name.length() == 0) {
						break;
					}
					temp.add(name);
				}
				PaneOrganizer gamePane = new PaneOrganizer(_stage, temp);
				ImageView quote = new ImageView(new Image(ClassLoader.getSystemResourceAsStream("Indy/__CoverOrganizer/quote.png")));
				quote.relocate(Constants.QUOTE_X, Constants.QUOTE_Y);
				gamePane.getRoot().getChildren().add(quote);
				Scene scene = new Scene(gamePane.getRoot());
				_stage.setScene(scene);
			}
		});

		_back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				_coverPane.getChildren().remove(1);
				_coverPane.getChildren().remove(2);
				_coverPane.getChildren().removeAll(_input, _start, _back);
				_coverPane.getChildren().addAll(_newGame, _tutorials, _exit);
			}
		});
	}

	public Pane getRoot() {
		return _coverPane;
	}
}

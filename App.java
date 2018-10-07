package Indy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This project features a game called Guillotine. This
 * class sets up the scene of the game and show it up on the screen.
 */
public class App extends Application {

	@Override
	public void start(Stage stage) {
		CoverOrganizer organizer = new CoverOrganizer(stage);
		Scene scene = new Scene(organizer.getRoot());
		stage.setScene(scene);
		this.setStage(stage);
		stage.show();
	}

	/**
	 * private helper method called in the start method.
	 *
	 * @param stage
	 */
	private void setStage(Stage stage) {
		stage.setTitle("Guillotine");
		stage.setX(Constants.USERSCREEN_X);
        stage.setY(Constants.USERSCREEN_Y);
        stage.setWidth(Constants.USERSCREEN_WIDTH);
        stage.setHeight(Constants.USERSCREEN_HEIGHT);
	}

	/*
	 * Here is the mainline! No need to change this.
	 */
	public static void main(String[] argv) {
		// launch is a static method inherited from Application.
		launch(argv);
	}
}

package Indy;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * Constants class that includes all constants used in this game
 */
public class Constants {

	private Constants() {
		throw new AssertionError("No instance");
	}

	public static final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	public static final double USERSCREEN_WIDTH = primaryScreenBounds.getWidth();
	public static final double USERSCREEN_HEIGHT = primaryScreenBounds.getHeight();
	public static final double USERSCREEN_X = primaryScreenBounds.getMinX();
	public static final double USERSCREEN_Y = primaryScreenBounds.getMinY();
	public static final int INITIAL_HAND_SIZE = 5;
	public static final int GLOBAL_SCORE_DIFF = 2;
	public static final int COMPANIONSHIP_BONUS = 4;
	public static final int STANDARD_QUEUE_SIZE = 12;
	public static final int MAXIMUM_HAND_SIZE = 12;
	public static final String[] ACTION_FOLDERS = { "Indy/support.", "Indy/simple.", "Indy/complex.", "Indy/cam." };
	public static final String[] NOBLE_FOLDERS = { "Indy/noble.", "Indy/immutable." };
	public static final String WORKSPACE = "";
	public static final int PROMPT_FACTOR_X = 1075;
	public static final int PROMPT_FACTOR_Y = 35;
	public static final int PROMPT_X = 700;
	public static final int PROMPT_Y = 500;
	public static final int INSPECT_X = 10;
	public static final int INSPECT_Y = 10;
	public static final double PROMPT_WIDTH = 400;
	public static final double PROMPT_HEIGHT = 200;
	public static final int SCENE_WIDTH = 1200;
	public static final int SCENE_HEIGHT = 700;
	public static final int UPROOT_X = 0;
	public static final int UPROOT_Y = 0;
	public static final double ROOTNODE_X = USERSCREEN_HEIGHT/2 + 100;
	public static final double ROOTNODE_Y = 250;
	public static final double UPROOT_HEIGHT = 3*USERSCREEN_HEIGHT/5 - 60;
	public static final double DOWNROOT_HEIGHT = USERSCREEN_HEIGHT - UPROOT_HEIGHT;
	public static final int START_X = 0;
	public static final int START_Y = 105;
	public static final int QUIT_X = START_X + 15;
	public static final int QUIT_Y = 210;
	public static final int RESTART_X = START_X;
	public static final int RESTART_Y = 100;
	public static final int QUOTE_X = 270;
	public static final int QUOTE_Y = -50;
	public static final int VIEW_WIDTH = 126;
	public static final int VIEW_HEIGHT = 180;
	public static final double ENLARGE_X = 15;
	public static final double ENLARGE_Y = 65;
	public static final double INPUT_X = USERSCREEN_WIDTH/2 + 150;
	public static final double INPUT_Y = 1.8*USERSCREEN_HEIGHT/5;
	public static final double GAMEOVER_X = USERSCREEN_WIDTH/2 - 300;
	public static final double GAMEOVER_Y = 2*USERSCREEN_HEIGHT/5;
	public static final double INPUT_WIDTH = 300;
	public static final double INPUT_HEIGHT = 150;
	public static final double INSTRUCTION_WIDTH = 500;
	public static final double INSTRUCTION_HEIGHT = 400;
	public static final double VIEW_ADJUSTMENT = 3.0 / 5;
	public static final int VIEW_X = 270;
	public static final int VIEW_Y = 60;
	public static final int ACTION_X = 220;
	public static final int ACTION_Y = 115;
	public static final double NEWGAME_X = USERSCREEN_WIDTH/2 - 135;
	public static final double NEWGAME_Y = USERSCREEN_HEIGHT/3 + 50;
	public static final double EXIT_X = USERSCREEN_WIDTH/2 - 95;
	public static final double EXIT_Y = USERSCREEN_HEIGHT/3 + 200;
	public static final double TUTORIALS_X = USERSCREEN_WIDTH/2 - 125;
	public static final double TUTORIALS_Y = USERSCREEN_HEIGHT/3 + 125;
	public static final double BACK_X = EXIT_X + 15;
	public static final double BACK_Y = EXIT_Y + 60;
	public static final double ANOTHERBACK_X = 3.5*USERSCREEN_WIDTH/5;
	public static final double ANOTHERBACK_Y = 4*USERSCREEN_HEIGHT/5;
}

/*
* Cellular Automata, ever expanding upon the first thesis project.
 */
package cellautomata;

import java.awt.Color;

/**
 * @author Dmitry Babich
 */
public class AppLauncher {
    
    // Window functionality constants
    private static final String TITLE = "Automata Cellulare";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static int fps = 60;
    
    //Logic basis constants
    private static final int COLS = 800, ROWS = 400;
    private static final int RANDOMIZER_PERCENT = 30;
    private static final int STARTING_S_RULE = 23;
    private static final int STARTING_B_RULE = 3;
    
    //Drawing constants
    private static final Color BACKGROUND_COLOR = Color.DARK_GRAY; //DARK_GRAY
    private static final Color ONCELL_COLOR = Color.BLUE;//new Color(128, 0, 50); //BLUE DEFAULT
    private static final Color START_FADE_COLOR = Color.CYAN;//new Color(128, 0, 50); //CYAN DEFAULT
    private static final Color END_FADE_COLOR = BACKGROUND_COLOR; //DARK_GRAY, BACKGROUND_COLOR to fade out
    private static final Color LINES_COLOR = Color.GRAY; //GRAY
    private static final float FADE_SPEED = 3; //FROM 1 to 255
    
    //Go to Display.java to edit JSwing elements' initial values
    
    public static void main(String[] args) {
        CellAutomata cellAutomata = new CellAutomata(TITLE, WIDTH, HEIGHT, fps, COLS, ROWS, STARTING_S_RULE, STARTING_B_RULE, BACKGROUND_COLOR, ONCELL_COLOR, LINES_COLOR, START_FADE_COLOR, END_FADE_COLOR, FADE_SPEED, RANDOMIZER_PERCENT);
        cellAutomata.start();
    }
    
}

import gui.Magic8BallFrame;
import util.FontLoader;

/**
 * Starts the whole program.
 *
 *  @author Maja P.
 */
public class Main {
    public static void main(String[] args) {
        // Loads the custom font before the window is shown.
        FontLoader.loadFonts();

        // Creates and opens the main application frame.
        new Magic8BallFrame();
    }
}
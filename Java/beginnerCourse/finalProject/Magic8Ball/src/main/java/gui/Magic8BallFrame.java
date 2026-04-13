package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Creates the main window of the Magic 8 Ball app.
 *
 * @author Maja P.
 */
public class Magic8BallFrame extends JFrame {

    /**
     * Sets up the main window.
     */
    public Magic8BallFrame() {
        // Sets the text shown in the window title bar.
        setTitle("Magic 8 Ball of Destiny");

        // Closes the program when the user closes the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Puts the custom background panel into the window.
        setContentPane(new BackgroundPanel());

        // Stops the window from getting too small.
        setMinimumSize(new Dimension(900, 600));

        // Starts the window in maximized mode.
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Makes the window visible on the screen.
        setVisible(true);
    }
}

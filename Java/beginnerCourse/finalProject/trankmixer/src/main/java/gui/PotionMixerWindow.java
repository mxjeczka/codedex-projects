package gui;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

/**
 * The main window class for the Potion Mixer game.
 * 
 * It extends JFrame and manages two main panels:
 * the main menu and the mixing panel.
 * 
 * @author Maja Mercedes Perz
 */
public class PotionMixerWindow extends JFrame {

    // The panel showing the main menu screen
    private final MainMenuPanel mainMenuPanel;

    // The panel where potion mixing happens
    private final MixingPanel mixingPanel;

    /**
     * Constructor to create the main window.
     * 
     * It sets up the window size, title, and behavior.
     * It also creates the main menu and mixing panels,
     * and shows the main menu first.
     */
    public PotionMixerWindow() {

        setTitle("Welcome to the magic World");// Set window title
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Exit the application when the window is closed

        // Get the usable screen area (excluding taskbar, dock, etc.)
        Rectangle usableBounds = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();

        setBounds(usableBounds); // Set the window size and position to fill the usable area
        setUndecorated(false); // Keep the title bar
        setResizable(false); // Prevent the user from resizing the window

        mainMenuPanel = new MainMenuPanel(this); // Create the main menu panel
        mixingPanel = new MixingPanel(this); // Create the mixing panel

        // Set the content pane to show the main menu first
        setContentPane(mainMenuPanel);

        setVisible(true); // Make the window visible
    }

    /**
     * Switch the displayed content to the mixing panel.
     * 
     * This method replaces the content pane with the mixing panel
     * and refreshes the window to show it.
     */
    public void switchToMixingPanel() {

        // Change content pane to mixing panel
        setContentPane(mixingPanel);

        // Refresh layout and redraw
        revalidate();
        repaint();
    }

    /**
     * Switch the displayed content back to the main menu panel.
     * 
     * Before switching, it resets the mixing panel to clear any state.
     * Then it shows the main menu panel again.
     */
    public void switchToMenuPanel() {

        // Reset mixing panel to its initial state
        mixingPanel.resetMixingPanel();

        // Change content pane back to main menu panel
        setContentPane(mainMenuPanel);

        // Refresh layout and redraw
        revalidate();
        repaint();
    }
}

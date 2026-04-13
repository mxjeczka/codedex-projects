package gui;

import logic.Magic8BallService;
import util.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;

import javax.swing.Timer;

/**
 * This class shows the background image, input field, button, and answer text.
 *
 *  @author Maja P.
 */
public class BackgroundPanel extends JPanel {
    // Stores the background image.
    private final Image backgroundImage;

    private final JLabel titleLabel;
    private final JTextField questionField;
    private final JButton askButton;
    private final JLabel answerLabel;

    // Creates random Magic 8 Ball answers.
    private final Magic8BallService magic8BallService;
    // Removes the answer after a few seconds.
    private Timer answerTimer;

    /**
     * Builds the whole panel and its components.
     */
    public BackgroundPanel() {
        // Loads the background image from the resources folder.
        URL imageUrl = getClass().getResource("/images/background.jpg");

        // Checks if the image file was found.
        if (imageUrl == null) {
            // Throws an error if the image file is missing.
            throw new IllegalStateException("Resource not found: /images/background.jpg");
        }

        // Creates the image object from the resource.
        backgroundImage = new ImageIcon(imageUrl).getImage();

        // Tells the panel to use no layout manager.
        setLayout(null);

        titleLabel = new JLabel("<html><center>Whisper<br>your question...</center></html>");
        titleLabel.setForeground(Color.WHITE);
        // Centers the title text inside the label.
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        questionField = new JTextField();
        questionField.setBackground(new Color(0x8b6eca));
        // Adds inner spacing to the input field.
        questionField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        questionField.setForeground(Color.WHITE);

        askButton = new JButton("Ask");
        askButton.setBackground(new Color(0x200076));
        askButton.setForeground(Color.WHITE);
        // Adds inner spacing to the button.
        askButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Creates the logic object for random answers.
        magic8BallService = new Magic8BallService();
        // Creates the answer label with empty text.
        answerLabel = new JLabel("");
        answerLabel.setForeground(Color.WHITE);
        answerLabel.setHorizontalAlignment(JLabel.CENTER);

        add(titleLabel);
        add(questionField);
        add(askButton);
        add(answerLabel);

        // Connects the button and text field with their logic.
        registerListeners();

        // Listens for window size changes.
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Recalculates fonts and positions after resizing.
                updateResponsiveStyles();
            }
        });

        // Updates the layout after Swing has finished creating the panel.
        SwingUtilities.invokeLater(this::updateResponsiveStyles);
    }

    /**
     * Draws the black background and the scaled image.
     *
     * @param g draws on the panel.
     */
    @Override
    public void paintComponent(Graphics g) {
        // Lets Swing paint the panel normally first.
        super.paintComponent(g);

        // Gets the position and size of the drawn image.
        Rectangle imageBounds = getImageDrawBounds();
        // Stops the method if the image size is not ready yet.
        if (imageBounds == null) {
            return;
        }

        g.setColor(Color.BLACK);
        // Fills the whole panel with black.
        g.fillRect(0, 0, getWidth(), getHeight());
        // Draws the scaled background image in the center.
        g.drawImage(
                backgroundImage,
                imageBounds.x,
                imageBounds.y,
                imageBounds.width,
                imageBounds.height,
                this
        );
    }

    /**
     * Updates font sizes and component positions after resizing.
     */
    private void updateResponsiveStyles() {
        // Gets the exact area where the image is drawn.
        Rectangle imageBounds = getImageDrawBounds();
        // Stops the method if the image size is not ready yet.
        if (imageBounds == null) {
            return;
        }

        // Finds the smaller side of the image area.
        int smallerSide = Math.min(imageBounds.width, imageBounds.height);

        float titleSize = Math.max(20f, smallerSide / 10f);
        float fieldSize = Math.max(18f, smallerSide / 28f);
        float buttonSize = Math.max(18f, smallerSide / 30f);
        float answerSize = Math.max(20f, smallerSide / 18f);

        titleLabel.setFont(FontLoader.getFont(titleSize));
        questionField.setFont(FontLoader.getFont(fieldSize));
        askButton.setFont(FontLoader.getFont(buttonSize));
        answerLabel.setFont(FontLoader.getFont(answerSize));

        // Places the title label relative to the image.
        titleLabel.setBounds(
                scaledX(imageBounds, 0.385),
                scaledY(imageBounds, 0.28),
                scaledWidth(imageBounds, 0.64),
                scaledHeight(imageBounds, 0.28)
        );

        // Places the question field relative to the image.
        questionField.setBounds(
                scaledX(imageBounds, 0.555),
                scaledY(imageBounds, 0.58),
                scaledWidth(imageBounds, 0.295),
                Math.max(40, scaledHeight(imageBounds, 0.055))
        );

        // Places the ask button relative to the image.
        askButton.setBounds(
                scaledX(imageBounds, 0.64),
                scaledY(imageBounds, 0.72),
                scaledWidth(imageBounds, 0.12),
                Math.max(42, scaledHeight(imageBounds, 0.06))
        );

        // Places the answer label relative to the image.
        answerLabel.setBounds(
                scaledX(imageBounds, -0.05),
                scaledY(imageBounds, 0.45),
                scaledWidth(imageBounds, 0.64),
                scaledHeight(imageBounds, 0.10)
        );

        repaint();
    }

    /**
     * Calculates where the background image is drawn.
     *
     * @return returns the x, y, width, and height of the image area.
     */
    private Rectangle getImageDrawBounds() {
        // Gets the current panel width.
        int panelWidth = getWidth();
        // Gets the current panel height.
        int panelHeight = getHeight();
        // Gets the original image width.
        int imageWidth = backgroundImage.getWidth(this);
        // Gets the original image height.
        int imageHeight = backgroundImage.getHeight(this);

        // Stops the method if sizes are not ready.
        if (panelWidth <= 0 || panelHeight <= 0 || imageWidth <= 0 || imageHeight <= 0) {
            return null;
        }

        // Calculates the correct scale factor while keeping the ratio.
        double scale = Math.min(
                (double) panelWidth / imageWidth,
                (double) panelHeight / imageHeight
        );

        // Calculates the drawn image width.
        int drawWidth = (int) Math.ceil(imageWidth * scale);
        // Calculates the drawn image height.
        int drawHeight = (int) Math.ceil(imageHeight * scale);
        // Centers the image on the x-axis.
        int x = (panelWidth - drawWidth) / 2;
        // Centers the image on the y-axis.
        int y = (panelHeight - drawHeight) / 2;

        // Returns the image area as a rectangle.
        return new Rectangle(x, y, drawWidth, drawHeight);
    }

    /**
     * Converts a relative x value into a real x position.
     *
     * @param bounds is the image area.
     * @param percent is the x position as a percent value.
     * @return returns the real x position.
     */
    private int scaledX(Rectangle bounds, double percent) {
        // Returns the x position inside the image area.
        return bounds.x + (int) Math.round(bounds.width * percent);
    }

    /**
     * Converts a relative y value into a real y position.
     *
     * @param bounds is the image area.
     * @param percent is the y position as a percent value.
     * @return returns the real y position.
     */
    private int scaledY(Rectangle bounds, double percent) {
        // Returns the y position inside the image area.
        return bounds.y + (int) Math.round(bounds.height * percent);
    }

    /**
     * Converts a relative width value into a real width.
     *
     * @param bounds is the image area.
     * @param percent is the width as a percent value.
     * @return returns the real width.
     */
    private int scaledWidth(Rectangle bounds, double percent) {
        // Returns the width inside the image area.
        return (int) Math.round(bounds.width * percent);
    }

    /**
     * Converts a relative height value into a real height.
     *
     * @param bounds is the image area.
     * @param percent is the height as a percent value.
     * @return returns the real height.
     */
    private int scaledHeight(Rectangle bounds, double percent) {
        // Returns the height inside the image area.
        return (int) Math.round(bounds.height * percent);
    }

    /**
     * Connects UI events to their logic.
     */
    private void registerListeners() {
        // Runs the ask logic when the button is clicked.
        askButton.addActionListener(e -> handleAskButton());

        // Listens for focus changes on the text field.
        questionField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clears the old answer and old question when the user clicks the field.
                resetQuestionInput();
            }
        });
    }

    /**
     * Clears the old answer and the old question text.
     */
    private void resetQuestionInput() {
        // Stops the timer if it is still running.
        if (answerTimer != null && answerTimer.isRunning()) {
            answerTimer.stop();
        }

        clearAnswer();
        questionField.setText("");
    }

    /**
     * Handles the click on the ask button.
     */
    private void handleAskButton() {
        // Gets the text from the input field and removes extra spaces.
        String question = questionField.getText().trim();

        // Checks if the user entered no question.
        if (question.isEmpty()) {
            clearAnswer();
            return;
        }

        // Gets one random answer from the logic class.
        String answer = magic8BallService.getRandomAnswer();
        showAnswer(answer);
        startAnswerTimer();
    }

    /**
     * Shows a new answer in the label.
     *
     * @param answer is the text that should be shown.
     */
    private void showAnswer(String answer) {
        // Puts the given answer text into the label.
        answerLabel.setText(answer);
    }

    /**
     * Clears the answer label.
     */
    private void clearAnswer() {
        // Removes all text from the answer label.
        answerLabel.setText("");
    }

    /**
     * Starts or restarts the timer for hiding the answer.
     */
    private void startAnswerTimer() {
        // Checks if an old timer is still running.
        if (answerTimer != null && answerTimer.isRunning()) {
            answerTimer.stop();
        }

        // Creates a timer that clears the answer after three seconds.
        answerTimer = new Timer(3000, e -> clearAnswer());
        // Makes sure the timer only runs one time.
        answerTimer.setRepeats(false);
        answerTimer.start();
    }
}

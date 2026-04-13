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

public class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    private final JLabel titleLabel;
    private final JTextField questionField;
    private final JButton askButton;
    private final JLabel answerLabel;

    private final Magic8BallService magic8BallService;
    private Timer answerTimer;


    public BackgroundPanel() {
        URL imageUrl = getClass().getResource("/images/background.jpg");

        if (imageUrl == null) {
            throw new IllegalStateException("Resource not found: /background.jpg");
        }

        backgroundImage = new ImageIcon(imageUrl).getImage();

        setLayout(null);

        titleLabel = new JLabel("<html><center>Whisper<br>your question...<center></html>");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        questionField = new JTextField();
        questionField.setBackground(new Color(0x8b6eca));
        questionField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        questionField.setForeground(Color.WHITE);

        askButton = new JButton("Ask");
        askButton.setBackground(new Color(0x200076));
        askButton.setForeground(Color.WHITE);
        askButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        magic8BallService = new Magic8BallService();
        answerLabel = new JLabel("");
        registerListeners();
        answerLabel.setForeground(Color.WHITE);
        answerLabel.setHorizontalAlignment(JLabel.CENTER);

        add(titleLabel);
        add(questionField);
        add(askButton);
        add(answerLabel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateResponsiveStyles();
            }
        });

        SwingUtilities.invokeLater(this::updateResponsiveStyles);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Rectangle imageBounds = getImageDrawBounds();
        if (imageBounds == null) {
            return;
        }

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(
                backgroundImage,
                imageBounds.x,
                imageBounds.y,
                imageBounds.width,
                imageBounds.height,
                this
        );
    }

    private void updateResponsiveStyles() {
        Rectangle imageBounds = getImageDrawBounds();
        if (imageBounds == null) {
            return;
        }

        int smallerSide = Math.min(imageBounds.width, imageBounds.height);

        float titleSize = Math.max(20f, smallerSide / 10f);
        float fieldSize = Math.max(18f, smallerSide / 28f);
        float buttonSize = Math.max(18f, smallerSide / 30f);
        float answerSize = Math.max(20f, smallerSide / 18f);

        titleLabel.setFont(FontLoader.getFont(titleSize));
        questionField.setFont(FontLoader.getFont(fieldSize));
        askButton.setFont(FontLoader.getFont(buttonSize));
        answerLabel.setFont(FontLoader.getFont(answerSize));

        titleLabel.setBounds(
                scaledX(imageBounds, 0.385),
                scaledY(imageBounds, 0.28),
                scaledWidth(imageBounds, 0.64),
                scaledHeight(imageBounds, 0.28)
        );

        questionField.setBounds(
                scaledX(imageBounds, 0.555),
                scaledY(imageBounds, 0.58),
                scaledWidth(imageBounds, 0.295),
                Math.max(40, scaledHeight(imageBounds, 0.055))
        );

        askButton.setBounds(
                scaledX(imageBounds, 0.64),
                scaledY(imageBounds, 0.72),
                scaledWidth(imageBounds, 0.12),
                Math.max(42, scaledHeight(imageBounds, 0.06))
        );

        answerLabel.setBounds(
                scaledX(imageBounds, -0.05),
                scaledY(imageBounds, 0.45),
                scaledWidth(imageBounds, 0.64),
                scaledHeight(imageBounds, 0.10)
        );

        repaint();
    }

    private Rectangle getImageDrawBounds() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int imageWidth = backgroundImage.getWidth(this);
        int imageHeight = backgroundImage.getHeight(this);

        if (panelWidth <= 0 || panelHeight <= 0 || imageWidth <= 0 || imageHeight <= 0) {
            return null;
        }

        double scale = Math.min(
                (double) panelWidth / imageWidth,
                (double) panelHeight / imageHeight
        );

        int drawWidth = (int) Math.ceil(imageWidth * scale);
        int drawHeight = (int) Math.ceil(imageHeight * scale);
        int x = (panelWidth - drawWidth) / 2;
        int y = (panelHeight - drawHeight) / 2;

        return new Rectangle(x, y, drawWidth, drawHeight);
    }

    private int scaledX(Rectangle bounds, double percent) {
        return bounds.x + (int) Math.round(bounds.width * percent);
    }

    private int scaledY(Rectangle bounds, double percent) {
        return bounds.y + (int) Math.round(bounds.height * percent);
    }

    private int scaledWidth(Rectangle bounds, double percent) {
        return (int) Math.round(bounds.width * percent);
    }

    private int scaledHeight(Rectangle bounds, double percent) {
        return (int) Math.round(bounds.height * percent);
    }

    private void registerListeners() {
        askButton.addActionListener(e -> handleAskButton());

        questionField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                resetQuestionInput();
            }
        });
    }

    private void resetQuestionInput() {
        if (answerTimer != null && answerTimer.isRunning()) {
            answerTimer.stop();
        }

        clearAnswer();
        questionField.setText("");
    }

    private void handleAskButton() {
        String question = questionField.getText().trim();

        if (question.isEmpty()) {
            clearAnswer();
            return;
        }

        String answer = magic8BallService.getRandomAnswer();
        showAnswer(answer);
        startAnswerTimer();
    }

    private void showAnswer(String answer) {
        answerLabel.setText(answer);
    }

    private void clearAnswer() {
        answerLabel.setText("");
    }

    private void startAnswerTimer() {
        if (answerTimer != null && answerTimer.isRunning()) {
            answerTimer.stop();
        }

        answerTimer = new Timer(3000, e -> clearAnswer());
        answerTimer.setRepeats(false);
        answerTimer.start();
    }
}

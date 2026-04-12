package gui;

import util.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

public class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    private final JLabel titleLabel;
    private final JTextField questionField;
    private final JButton askButton;
    private final JLabel answerLabel;

    private final String[] answers = {"Yes", "No", "Ask again later", "Definitely"};

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

        answerLabel = new JLabel("Your answer will appear here");
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

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int imageWidth = backgroundImage.getWidth(this);
        int imageHeight = backgroundImage.getHeight(this);

        if (imageWidth <= 0 || imageHeight <= 0) {
            return;
        }

        double scale = Math.min(
                (double) panelWidth / imageWidth,
                (double) panelHeight / imageHeight
        );

        int drawWidth = (int) Math.ceil(imageWidth * scale);
        int drawHeight = (int) Math.ceil(imageHeight * scale);
        int x = (panelWidth - drawWidth) / 2;
        int y = (panelHeight - drawHeight) / 2;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panelWidth, panelHeight);
        g.drawImage(backgroundImage, x, y, drawWidth, drawHeight, this);
    }

    private void updateResponsiveStyles() {
        int panelWidth = Math.max(getWidth(), 800);
        int panelHeight = Math.max(getHeight(), 600);
        int smallerSide = Math.min(panelWidth, panelHeight);

        float titleSize = Math.max(20f, smallerSide / 10f);
        float fieldSize = Math.max(18f, smallerSide / 28f);
        float buttonSize = Math.max(18f, smallerSide / 30f);
        float answerSize = Math.max(20f, smallerSide / 24f);

        titleLabel.setFont(FontLoader.getFont(titleSize));
        questionField.setFont(FontLoader.getFont(fieldSize));
        askButton.setFont(FontLoader.getFont(buttonSize));
        answerLabel.setFont(FontLoader.getFont(answerSize));

        titleLabel.setBounds(
                scaledX(panelWidth, 0.35),
                scaledY(panelHeight, 0.28),
                scaledWidth(panelWidth, 0.64),
                scaledHeight(panelHeight, 0.28)
        );

        questionField.setBounds(
                scaledX(panelWidth, 0.545),
                scaledY(panelHeight, 0.58),
                scaledWidth(panelWidth, 0.23),
                Math.max(40, scaledHeight(panelHeight, 0.055))
        );

        askButton.setBounds(
                scaledX(panelWidth, 0.60),
                scaledY(panelHeight, 0.72),
                scaledWidth(panelWidth, 0.12),
                Math.max(42, scaledHeight(panelHeight, 0.06))
        );

        answerLabel.setBounds(
                scaledX(panelWidth, 0.18),
                scaledY(panelHeight, 0.56),
                scaledWidth(panelWidth, 0.64),
                scaledHeight(panelHeight, 0.10)
        );

        repaint();
    }

    private int scaledX(int width, double percent) {
        return (int) Math.round(width * percent);
    }

    private int scaledY(int height, double percent) {
        return (int) Math.round(height * percent);
    }

    private int scaledWidth(int width, double percent) {
        return (int) Math.round(width * percent);
    }

    private int scaledHeight(int height, double percent) {
        return (int) Math.round(height * percent);
    }
}

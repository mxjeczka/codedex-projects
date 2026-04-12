package gui;

import util.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

public class BackgroundPanel extends JPanel {
    private final Image backgroundImage;
    private final JPanel contentPanel;

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

        setLayout(new GridBagLayout());

        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        titleLabel = new JLabel("<html><center>Whisper<br>your question...<center></html>");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        questionField = new JTextField();
        askButton = new JButton("Ask");
        answerLabel = new JLabel("Your answer will appear here");
        answerLabel.setForeground(Color.WHITE);
        answerLabel.setHorizontalAlignment(JLabel.CENTER);

        addRow(titleLabel, 0, 200, 20);
        addRow(questionField, 1, 0, 16);
        addRow(askButton, 2, 0, 16);
        addRow(answerLabel, 3, 0, 0);

        add(contentPanel, centeredConstraints());

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

    private GridBagConstraints centeredConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        return gbc;
    }

    private void addRow(JComponent component, int row, int top, int bottom) {
        contentPanel.add(component, rowConstraints(row, top, bottom));
    }

    private GridBagConstraints rowConstraints(int row, int top, int bottom) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(top, 0, bottom, 0);
        return gbc;
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

        int contentWidth = Math.max(320, panelWidth / 3);
        questionField.setPreferredSize(new Dimension(contentWidth, Math.max(40, panelHeight / 18)));
        askButton.setPreferredSize(new Dimension(Math.max(180, panelWidth / 8), Math.max(42, panelHeight / 17)));

        int topPadding = Math.max(30, panelHeight / 12);
        int sidePadding = Math.max(20, panelWidth / 16);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(topPadding, sidePadding, 0, sidePadding));

        revalidate();
        repaint();
    }
}

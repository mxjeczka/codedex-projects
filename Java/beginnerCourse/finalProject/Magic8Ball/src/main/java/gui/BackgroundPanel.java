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

        titleLabel = new JLabel("Background Panel");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        questionField = new JTextField();
        askButton = new JButton("Ask again");
        answerLabel = new JLabel(" ");

        contentPanel.add(titleLabel, gbc(0, 0));
//        contentPanel.add(questionField);
//        contentPanel.add(askButton);
//        contentPanel.add(answerLabel);
        add(contentPanel, containerGbc());

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

    private GridBagConstraints containerGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        return gbc;
    }

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    private void updateResponsiveStyles() {
        int panelWidth = Math.max(getWidth(), 800);
        int panelHeight = Math.max(getHeight(), 600);
        int smallerSide = Math.min(panelWidth, panelHeight);

        float titleSize = Math.max(28f, smallerSide / 10f);
        titleLabel.setFont(FontLoader.getFont(titleSize));

        int topPadding = Math.max(30, panelHeight / 12);
        int sidePadding = Math.max(20, panelWidth / 16);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(topPadding, sidePadding, 0, sidePadding));

        revalidate();
        repaint();
    }
}

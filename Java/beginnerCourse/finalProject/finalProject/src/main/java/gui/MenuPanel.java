package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 45;
    private static final int BOTTOM_MARGIN = 40;

    private final PotionMixerWindow window;
    private final Image backgroundImage;
    private final JButton startButton;

    public MenuPanel(PotionMixerWindow window) {
        this.window = window;
        setLayout(null);

        startButton = new JButton("Play");
        startButton.setSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        startButton.addActionListener(e -> this.window.switchToMixingPanel());
        add(startButton);

        try {
            URL imageUrl = getClass().getResource("/enviroment/huette.png");
            if (imageUrl == null) {
                throw new IllegalArgumentException("Ressource '/enviroment/huette.png' wurde nicht gefunden.");
            }
            backgroundImage = ImageIO.read(imageUrl);
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Bild '/enviroment/huette.png' konnte nicht geladen werden.", e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void doLayout() {
        super.doLayout();
        int x = (getWidth() - BUTTON_WIDTH) / 2;
        int y = getHeight() - BUTTON_HEIGHT - BOTTOM_MARGIN;
        startButton.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }
}

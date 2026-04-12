package gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel() {
        URL imageUrl = getClass().getResource("/background.jpg");

        if (imageUrl == null) {
            throw new IllegalStateException("Resource not found: /background.jpg");
        }

        backgroundImage = new ImageIcon(imageUrl).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

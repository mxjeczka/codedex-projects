package gui;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MixingPanel extends JPanel {
    private static final int BUTTON_WIDTH = 165;
    private static final int BUTTON_HEIGHT = 45;
    private static final int RIGHT_MARGIN = 40;
    private static final int BOTTOM_MARGIN = 40;

    private final PotionMixerWindow window;
    private final Image backgroundImage;
    private final JButton backButton;

    public MixingPanel(PotionMixerWindow window) {
        this.window = window;
        setLayout(null);

        backButton = new JButton("Back to Menu");
        backButton.setBackground(Color.decode("#F8C8DC"));
        backButton.setFont(AppFonts.alagard(20f));
        backButton.setSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        backButton.addActionListener(e -> this.window.switchToMenuPanel());
        add(backButton);

        try {
            URL imageUrl = getClass().getResource("/enviroment/alchemyLabor.png");
            if (imageUrl == null) {
                throw new IllegalArgumentException("Ressource '/enviroment/alchemyLabor.png' wurde nicht gefunden.");
            }
            backgroundImage = ImageIO.read(imageUrl);
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Bild '/enviroment/alchemyLabor.png' konnte nicht geladen werden.", e);
        }
    }

    public void resetMixingPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void doLayout() {
        super.doLayout();
        int x = getWidth() - BUTTON_WIDTH - RIGHT_MARGIN;
        int y = getHeight() - BUTTON_HEIGHT - BOTTOM_MARGIN;
        backButton.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }
}

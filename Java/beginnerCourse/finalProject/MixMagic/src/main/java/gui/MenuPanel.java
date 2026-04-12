package gui;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 60;
    private static final int BOTTOM_MARGIN = 60;
    private static final String TITLE_TEXT = "AlchemyLab";
    private static final String AUTHOR_TEXT = "By Maja";

    private final PotionMixerWindow window;
    private final Image backgroundImage;
    private final JButton startButton;

    public MenuPanel(PotionMixerWindow window) {
        this.window = window;
        setLayout(null);

        startButton = new JButton("Play");
        startButton.setBackground(Color.decode("#F8C8DC"));
        startButton.setFont(AppFonts.alagard(40f));
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
        Graphics2D g2 = (Graphics2D) g.create();
        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        drawMenuText(g2);
        g2.dispose();
    }

    @Override
    public void doLayout() {
        super.doLayout();
        int x = (getWidth() - BUTTON_WIDTH) / 2;
        int y = getHeight() - BUTTON_HEIGHT - BOTTOM_MARGIN;
        startButton.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void drawMenuText(Graphics2D g2) {
        float scale = Math.min(getWidth() / 1920f, getHeight() / 1080f);
        int outlineSize = Math.max(2, Math.round(3 * scale));

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font titleFont = AppFonts.alagard(82f * scale);
        int titleX = (int) (getWidth() * 0.18);
        int titleY = (int) (getHeight() * 0.48);
        drawOutlinedText(g2, TITLE_TEXT, titleFont, titleX, titleY, outlineSize);

        Font authorFont = AppFonts.alagard(34f * scale);
        int authorX = (int) (getWidth() * 0.03);
        int authorY = (int) (getHeight() * 0.95);
        drawOutlinedText(g2, AUTHOR_TEXT, authorFont, authorX, authorY, outlineSize);
    }

    private void drawOutlinedText(Graphics2D g2, String text, Font font, int x, int y, int outlineSize) {
        g2.setFont(font);
        g2.setColor(Color.BLACK);

        for (int dx = -outlineSize; dx <= outlineSize; dx++) {
            for (int dy = -outlineSize; dy <= outlineSize; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                g2.drawString(text, x + dx, y + dy);
            }
        }

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }
}

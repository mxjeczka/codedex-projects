package gui;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.InputStream;
import javax.swing.*;

import util.ImageLoader;

public class MainMenuPanel extends JPanel {

    private final PotionMixerWindow parent;
    private Image mainMenuBackground;

    private JButton playButton;

    private Font baseTitleFont;
    private Font baseSubtitleFont;

    private Image originalPlayImage;


    public MainMenuPanel(PotionMixerWindow parent) {
        this.parent = parent;

        loadImages();

        setLayout(new GridBagLayout()); // ✅ KEIN null layout mehr

        playButton = createPlayButton();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weighty = 1.0; // 👈 DAS ist der Gamechanger
        gbc.anchor = GridBagConstraints.SOUTH;

        gbc.insets = new Insets(0, 0, 50, 0);

        add(playButton, gbc);


        loadFonts();

        // responsive scaling trigger
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateButtonSize();
                repaint();
            }
        });
    }

    // ---------------- BUTTON ----------------

    private JButton createPlayButton() {

        originalPlayImage = ImageLoader
                .loadImage("/images/pixelPlayButton.png", "Play Button")
                .getScaledInstance(192, 78, Image.SCALE_SMOOTH);

        JButton button = new JButton(new ImageIcon(originalPlayImage));

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        button.addActionListener(e -> parent.switchToMixingPanel());

        return button;
    }


    private void updateButtonSize() {

        float scale = Math.min(getWidth() / 1920f, getHeight() / 1080f);

        int width = (int) (192 * scale);
        int height = (int) (78 * scale);

        Image scaled = originalPlayImage.getScaledInstance(
                width,
                height,
                Image.SCALE_SMOOTH
        );

        playButton.setIcon(new ImageIcon(scaled));

        playButton.setPreferredSize(new Dimension(width, height));
        playButton.revalidate();
    }


    // ---------------- BACKGROUND ----------------

    private void loadImages() {
        mainMenuBackground = ImageLoader.loadImage("/images/huette.png", "huette.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Background
        if (mainMenuBackground != null) {
            g2.drawImage(mainMenuBackground, 0, 0, getWidth(), getHeight(), this);
        }

        drawText(g2);
    }

    // ---------------- TEXT ----------------

    private void loadFonts() {
        try (InputStream is = getClass().getResourceAsStream("/fonts/alagard.ttf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);

            baseTitleFont = font.deriveFont(72f);
            baseSubtitleFont = font.deriveFont(28f);

        } catch (Exception e) {
            baseTitleFont = new Font("Serif", Font.BOLD, 72);
            baseSubtitleFont = new Font("Serif", Font.PLAIN, 28);
        }
    }

    private void drawText(Graphics2D g2) {

        float scale = Math.min(getWidth() / 1920f, getHeight() / 1080f);

        Font titleFont = baseTitleFont.deriveFont(72f * scale);
        Font subtitleFont = baseSubtitleFont.deriveFont(35f * scale);

        String title = "AlchemyLab";
        String subtitle = "By: Maja";

        int shadow = (int) (4 * scale);

        // ================= TITLE (mittig links) =================
        g2.setFont(titleFont);

        FontMetrics fm = g2.getFontMetrics();

        int titleX = (int) (getWidth() * 0.20); // links mit Abstand
        int titleY = getHeight() / 2;           // vertikal mittig

        g2.setColor(Color.BLACK);
        g2.drawString(title, titleX + shadow, titleY + shadow);

        g2.setColor(Color.WHITE);
        g2.drawString(title, titleX, titleY);

        // ================= SUBTITLE (links unten) =================
        g2.setFont(subtitleFont);

        FontMetrics fm2 = g2.getFontMetrics();

        int subtitleX = (int) (getWidth() * 0.03);
        int subtitleY = (int) (getHeight() * 0.95);

        g2.setColor(Color.BLACK);
        g2.drawString(subtitle, subtitleX + shadow, subtitleY + shadow);

        g2.setColor(Color.WHITE);
        g2.drawString(subtitle, subtitleX, subtitleY);
    }

}

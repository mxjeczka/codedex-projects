package util;

import java.awt.*;
import java.io.InputStream;

public class FontLoader {

    private static Font magicFont;

    public static void loadFonts() {
        try {
            InputStream font = FontLoader.class.getResourceAsStream("/font/MagicSchoolOne.ttf");

            magicFont = Font.createFont(Font.TRUETYPE_FONT, font);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(magicFont);

        } catch (Exception e) {
            e.printStackTrace();

            // Fallback, falls Font nicht lädt
            magicFont = new Font("Serif", Font.PLAIN, 18);
        }
    }

    public static Font getFont(float size) {
        return magicFont.deriveFont(size);
    }
}


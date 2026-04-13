package util;

import java.awt.*;
import java.io.InputStream;

/**
 * This class loads and returns the custom font.
 *
 *  @author Maja P.
 */
public class FontLoader {

    // Stores the custom font after loading.
    private static Font magicFont;

    /**
     * Loads the custom font from the resources folder.
     */
    public static void loadFonts() {
        try {
            // Opens the font file from the resources folder.
            InputStream font = FontLoader.class.getResourceAsStream("/font/MagicSchoolOne.ttf");
            // Checks if the font file was found.
            if (font == null) {
                // Throws an error when the font file is missing.
                throw new IllegalStateException("Resource not found: /font/MagicSchoolOne.ttf");
            }

            // Creates a Font object from the font file.
            magicFont = Font.createFont(Font.TRUETYPE_FONT, font);

            // Gets the local graphics environment of the system.
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // Registers the custom font so the app can use it.
            ge.registerFont(magicFont);

        } catch (Exception e) {
            // Prints the error to the console.
            e.printStackTrace();

            //Uses a simple fallback font if loading fails.
            magicFont = new Font("Serif", Font.PLAIN, 18);
        }
    }

    /**
     * This method returns the custom font in a specific size.
     *
     * @param size is the font size.
     * @return the custom font with the given size.
     */
    public static Font getFont(float size) {
        // Returns the loaded font with a new size.
        return magicFont.deriveFont(size);
    }
}
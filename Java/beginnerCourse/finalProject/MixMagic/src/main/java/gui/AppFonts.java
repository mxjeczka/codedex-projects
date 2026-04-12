package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public final class AppFonts {
    private static final String ALAGARD_PATH = "/font/alagard.ttf";
    private static final Font ALAGARD_BASE_FONT = loadFont(ALAGARD_PATH);

    private AppFonts() {
    }

    public static Font alagard(float size) {
        return ALAGARD_BASE_FONT.deriveFont(Font.PLAIN, size);
    }

//    public static Font alagardBold(float size) {
//        return ALAGARD_BASE_FONT.deriveFont(Font.BOLD, size);
//    }

    private static Font loadFont(String resourcePath) {
        try (InputStream fontStream = AppFonts.class.getResourceAsStream(resourcePath)) {
            if (fontStream == null) {
                throw new IllegalArgumentException("Ressource '" + resourcePath + "' wurde nicht gefunden.");
            }

            return Font.createFont(Font.TRUETYPE_FONT, fontStream);
        } catch (FontFormatException | IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Schrift '" + resourcePath + "' konnte nicht geladen werden.", e);
        }
    }
}

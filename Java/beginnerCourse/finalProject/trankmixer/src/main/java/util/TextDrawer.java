package util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;

/**
 * This class provides a method to draw text with an outline.
 * 
 * This is useful for making text easier to read on colorful or complex backgrounds.
 * 
 * @author Maja Mercedes Perz
 */
public class TextDrawer {

    /**
     * Draws outlined text using the given font, position, and colors.
     *
     * @param g2 the Graphics2D object used to draw on the screen
     * @param text the text that should be drawn
     * @param font the font used for the text
     * @param x the x-coordinate where the text starts
     * @param y the y-coordinate where the text starts
     * @param outline the color used for the outline of the text
     * @param fill the color used to fill the inside of the text
     */
    public static void drawOutlinedText(Graphics2D g2, String text, Font font, int x, int y, Color outline, Color fill) {
        // Create a vector outline of the text using the given font
        GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(), text);

        // Get the outline shape of the text at position (x, y)
        Shape shape = gv.getOutline(x, y);

        // Set the color for the outline (border of the text)
        g2.setColor(outline);

        // Set the thickness of the outline
        g2.setStroke(new BasicStroke(4));

        // Draw the text outline
        g2.draw(shape);

        // Set the color for the inside of the text
        g2.setColor(fill);

        // Fill the inside of the text with the fill color
        g2.fill(shape);
    }
    
    /**
     * Draws plain (non-outlined) text with the given font and color.
     */
    public static void drawPlainText(Graphics2D g2, String text, Font font, int x, int y, Color color) {
        g2.setFont(font);
        g2.setColor(color);
        g2.drawString(text, x, y);
    }

}

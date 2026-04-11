package util;

import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This class is a helper class to load and resize images.
 * 
 * It prints error messages if an image cannot be loaded.
 * 
 * @author Maja Mercedes Perz
 */
public class ImageLoader {

    /**
     * Loads an image from the resource path.
     *
     * @param path         the path to the image file
     * @param nameForError a name to include in the error message if the loading
     *                     fails
     * @return the loaded Image, or null if loading fails
     */
    public static Image loadImage(String path, String nameForError) {
        // Open a stream to read the image file from the resource folder
        try (InputStream stream = ImageLoader.class.getResourceAsStream(path)) {
            // Try to read the image from the stream
            return ImageIO.read(stream);
        } catch (Exception e) {
            // If there is any error, print a message with the given name
            System.err.println("Error loading " + nameForError);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads an image from the resource path and scales it to the given width and
     * height.
     *
     * @param path         the path to the image file
     * @param width        the width of the image
     * @param height       the height of the image
     * @param nameForError a name to include in the error message if loading fails
     * @return the scaled ImageIcon, or null if loading fails
     */
    public static ImageIcon loadAndScaleImageIcon(String path, int width, int height, String nameForError) {
        // Load the original image from the resource path
        try {
            ImageIcon original = new ImageIcon(ImageLoader.class.getResource(path));

            // Scale the image to the given width and height with smooth scaling
            Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Return the scaled image as an ImageIcon
            return new ImageIcon(scaled);
        } catch (Exception e) {
            // If there is any error, print a message with the given name
            System.err.println("Error loading " + nameForError);
            e.printStackTrace();
            return null;
        }
    }
}

package util;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public final class ImageLoader {
    private ImageLoader() {
    }

    public static ImageIcon loadAndScaleImageIcon(String resourcePath, int width, int height, String assetName) {
        URL resourceUrl = ImageLoader.class.getResource(resourcePath);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Ressource '" + resourcePath + "' für '" + assetName + "' wurde nicht gefunden.");
        }

        ImageIcon icon = new ImageIcon(resourceUrl);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}

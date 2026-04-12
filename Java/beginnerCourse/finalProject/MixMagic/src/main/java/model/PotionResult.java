package model;

import javax.swing.ImageIcon;

public class PotionResult {
    private final String name;
    private final String description;
    private final ImageIcon icon;

    public PotionResult(String name, String description, ImageIcon icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ImageIcon getIcon() {
        return icon;
    }
}

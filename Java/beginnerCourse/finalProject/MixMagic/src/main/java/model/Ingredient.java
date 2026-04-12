package model;

import javax.swing.ImageIcon;

public class Ingredient {
    private final String name;
    private final ImageIcon icon;
    private final ImageIcon selectedIcon;

    public Ingredient(String name, ImageIcon icon, ImageIcon selectedIcon) {
        this.name = name;
        this.icon = icon;
        this.selectedIcon = selectedIcon;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public ImageIcon getSelectedIcon() {
        return selectedIcon;
    }
}

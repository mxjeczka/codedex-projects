package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.InputStream;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.PotionCombiner;
import logic.PotionDescriptions;
import util.ImageLoader;
import util.TextDrawer;

/**
 * MixingPanel is the main game screen where players combine two ingredients to
 * brew potions.
 * 
 * It shows ingredient buttons for selection, a mix button to create potions,
 * and displays the result potion image along with descriptions.
 * 
 * The panel also shows a shelf with mini-icons of brewed potions,
 * a wizard character that changes expression, and sound control buttons.
 * 
 * Players can reset the panel or go back to the main menu using provided
 * controls.
 * This class handles all interactions and visual updates related to potion
 * mixing.
 * 
 * @author Maja Mercedes Perz
 */
public class MixingPanel extends JPanel {

    // Reference to the parent window to call panel switching methods
    private final PotionMixerWindow parent;

    // Background image for the mixing screen
    private Image mixingBackground;

    // Selected ingredients, start as null (no selection)
    private String ingredient1 = null;
    private String ingredient2 = null;

    // Label to display the resulting potion and wizard image
    private JLabel resultLabel;
    private JLabel wizardLabel;

    // Buttons for each ingredient
    private JButton eyeButton;
    private JButton crystalButton;
    private JButton mushroomsButton;
    private JButton airButton;
    private JButton fireButton;
    private JButton waterButton;
    private JButton earthButton;
    private JButton soundOnButton;
    private JButton soundOffButton;

    // Icons for normal and selected states of ingredient buttons
    private ImageIcon eyeIcon, eyeSelectedIcon;
    private ImageIcon crystalIcon, crystalSelectedIcon;
    private ImageIcon mushroomsIcon, mushroomsSelectedIcon;
    private ImageIcon waterIcon, waterSelectedIcon;
    private ImageIcon fireIcon, fireSelectedIcon;
    private ImageIcon airIcon, airSelectedIcon;
    private ImageIcon earthIcon, earthSelectedIcon;

    // Boolean flags for game states
    private boolean allPotionsBrewed = false;
    private boolean finalMixDone = false;

    // List to store potion icons shown on the shelf
    // Set to keep track of unique brewed potion combinations
    private final java.util.List<JLabel> potionShelfIcons = new java.util.ArrayList<>();
    private final java.util.Set<String> brewedPotions = new java.util.HashSet<>();

    private String[] topWizardLines = {
            "Welcome, young alchemist!",
            "You've stepped into ancient secrets.",
            "Here, herbs whisper,",
            "cauldrons bubble, and secrets brew.",
            "Combine two mystic ingredients...",
            "and let the magic begin." };


    /**
     * Constructor for MixingPanel.
     * 
     * Stores reference to parent window, loads images, sets layout,
     * and creates buttons and other UI elements.
     * 
     * @param parent The PotionMixerWindow that contains this panel
     */
    public MixingPanel(PotionMixerWindow parent) {
        this.parent = parent; // Save parent window reference
        loadImages(); // Load background image
        setLayout(null); // Use absolute positioning
        loadIngredientIcons(); // Loads ingredient icons
        createButtonsAndUI(); // Create all buttons and UI elements

    }

    /**
     * Loads the background image for the mixing panel.
     */
    private void loadImages() {
        mixingBackground = ImageLoader.loadImage("/images/alchemyLabor.png", "alchemyLabor.png");
    }

    /**
     * Loads all ingredient icons (normal and selected versions).
     */
    private void loadIngredientIcons() {
        eyeIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/Eye.png", 121, 121, "Eye");
        eyeSelectedIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/Eye_selected.png", 121, 121, "Eye Selected");

        crystalIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/Crystal.png", 121, 121, "Crystal");
        crystalSelectedIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/Crystal_selected.png", 121, 121,
                "Crystal Selected");

        mushroomsIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/Mushrooms.png", 121, 121, "Mushrooms");
        mushroomsSelectedIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/Mushrooms_selected.png", 121, 121,
                "Mushrooms Selected");

        waterIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/water.png", 90, 90, "Water");
        waterSelectedIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/water_selected.png", 90, 90, "Water Selected");

        fireIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/fire.png", 98, 98, "Fire");
        fireSelectedIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/fire_selected.png", 98, 98, "Fire Selected");

        airIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/air.png", 92, 92, "Air");
        airSelectedIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/air_selected.png", 92, 92, "Air Selected");

        earthIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/earth.png", 96, 96, "Earth");
        earthSelectedIcon = ImageLoader.loadAndScaleImageIcon("/zutaten/earth_selected.png", 96, 96, "Earth Selected");
    }

    /**
     * Draw the background, speech bubble, and the task text.
     * 
     * @param g Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paint background and children

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Draw background image stretched to fill panel if available
        if (mixingBackground != null) {
            g.drawImage(mixingBackground, 0, 0, getWidth(), getHeight(), this);
        }

        // Create a Graphics2D object for better drawing control
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable smooth text and shape edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int bubbleWidth = (int) (panelWidth * 0.3);
        int bubbleHeight = (int) (bubbleWidth * 0.75);
        int bubbleX = (int) (panelWidth * 0.7);
        int bubbleY = (int) (panelHeight * 0.07);

        // Load and draw speech bubble image if available
        ImageIcon speechBubbleIcon = ImageLoader.loadAndScaleImageIcon("/images/speachBubble.png", 500, 374,
                "SpeechBubble");
        if (speechBubbleIcon != null) {
            g2.drawImage(speechBubbleIcon.getImage(), bubbleX, bubbleY, bubbleWidth, bubbleHeight, this);
        }

        Font taskFont; // Variable to hold font for drawing task text

        // Try to load custom font from resource, fallback to Serif if fail
        try (InputStream is = getClass().getResourceAsStream("/fonts/alagard.ttf")) {

            float fontSize = panelHeight * 0.028f;
            taskFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize);
        } catch (Exception e) {
            float fontSize = panelHeight * 0.028f;
            taskFont = new Font("Serif", Font.PLAIN, (int) fontSize);
        }

        int textX = (int) (panelWidth * 0.72);
        int textY = (int) (panelHeight * 0.18);

        int lineHeight = (int) (taskFont.getSize() * 1.2); // Zeilenabstand

        // Text zeilenweise zeichnen
        for (String line : topWizardLines) {
            TextDrawer.drawPlainText(g2, line, taskFont, textX, textY, Color.BLACK);
            textY += lineHeight;
        }

        g2.dispose(); // Clean up the Graphics2D object to free system resources
    }

    /**
     * Creates all buttons and UI components on this panel.
     * 
     * Adds back button, ingredient buttons, mix button, and parchment image.
     */
    private void createButtonsAndUI() {
        addBackButton();
        addIngredientButtons();
        addMixButton();
        addParchment();
        addWizard();
        addShelf();
    }

    /**
     * Adds a back button that switches to the main menu when clicked.
     */
    private void addBackButton() {
        ImageIcon icon = ImageLoader.loadAndScaleImageIcon("/images/pixelBackButton.png", 118, 50, "Back-Button");
        if (icon != null) {
            JButton backButton = new JButton(icon);

            int btnWidth = icon.getIconWidth();
            int btnHeight = icon.getIconHeight();

            backButton.setBounds(0, 0, btnWidth, btnHeight);

            backButton.setBorderPainted(false); // Remove border
            backButton.setContentAreaFilled(false); // Remove background
            backButton.setFocusPainted(false); // Remove focus highlight
            backButton.addActionListener(e -> parent.switchToMenuPanel()); // Switch to menu on click
            add(backButton);

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int panelWidth = getWidth();
                    int panelHeight = getHeight();

                    int x = (int) (panelWidth * 0.93);

                    int y = (int) (panelHeight * 0.95) - btnHeight;

                    backButton.setBounds(x, y, btnWidth, btnHeight);
                }
            });
        }
    }

    /**
     * Creates a ingredient button with icon and action.
     * 
     * @param path    Path to the ingredient image
     * @param name    Name of the ingredient
     * @return The created JButton
     */
    private JButton createIngredientButton(String path, String name, double relX, double relY, int width, int height) {
        ImageIcon icon = ImageLoader.loadAndScaleImageIcon(path, width, height, name);
        JButton button = new JButton(icon);

        button.setBorderPainted(false); // Remove border
        button.setContentAreaFilled(false); // Remove background
        button.setFocusPainted(false); // Remove focus highlight
        button.addActionListener(e -> setIngredient(name)); // Set ingredient when clicked
        add(button);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                int x = (int) (panelWidth * relX);
                int y = (int) (panelHeight * relY);

                button.setBounds(x, y, width, height);
            }
        });
        return button;
    }

    /**
     * Adds buttons for each ingredient to the panel.
     */
    private void addIngredientButtons() {
        eyeButton = createIngredientButton("/zutaten/Eye.png", "Eye", 0.05, 0.05, 121, 121);
        crystalButton = createIngredientButton("/zutaten/Crystal.png", "Crystal", 0.05, 0.18, 121, 121);
        mushroomsButton = createIngredientButton("/zutaten/Mushrooms.png", "Mushrooms", 0.05, 0.32, 121, 121);
        airButton = createIngredientButton("/zutaten/air.png", "Air", 0.04, 0.60, 92, 92);
        fireButton = createIngredientButton("/zutaten/fire.png", "Fire", 0.09, 0.48, 98, 98);
        waterButton = createIngredientButton("/zutaten/water.png", "Water", 0.045, 0.49, 90, 90);
        earthButton = createIngredientButton("/zutaten/earth.png", "Earth", 0.09, 0.60, 96, 96);
    }

    /**
     * Adds the mix button which combines selected ingredients when clicked.
     */
    private void addMixButton() {
        ImageIcon mixIcon = ImageLoader.loadAndScaleImageIcon("/images/pixelMixButton.png", 220, 90, "Mix-Button");
        if (mixIcon != null) {
            JButton mixButton = new JButton(mixIcon);
            mixButton.setBounds(730, 775, 220, 90);
            mixButton.setBorderPainted(false);
            mixButton.setContentAreaFilled(false);
            mixButton.setFocusPainted(false);

            // Add an action listener to handle clicks on the mix button
            mixButton.addActionListener(e -> {
                // Check if all potions have NOT been brewed yet
                if (!allPotionsBrewed) {
                    // If two ingredients are selected
                    if (ingredient1 != null && ingredient2 != null) {
                        // Combine the two ingredients to get the potion name
                        String potionName = PotionCombiner.combine(ingredient1, ingredient2);

                        // Show the image of the resulting potion
                        showResultImage(potionName);

                        // Normalize the ingredient combination so order does not matter
                        String normalized = normalizeCombination(ingredient1, ingredient2);

                        // If this potion combination was not brewed before
                        if (!brewedPotions.contains(normalized)) {
                            brewedPotions.add(normalized); // Add it to the list of brewed potions
                            addPotionToShelf(potionName); // Add a small icon of the potion on the shelf
                        }

                        // Load the neutral wizard image to reset wizard expression
                        ImageIcon neutralWizardIcon = ImageLoader.loadAndScaleImageIcon("/images/neutral_wizard.png",
                                387, 589, "Neutral Wizard");

                        // If neutral wizard image and label exist, update wizard picture
                        if (neutralWizardIcon != null && wizardLabel != null) {
                            wizardLabel.setIcon(neutralWizardIcon);
                        }

                        // Clear the top wizard text lines for now
                        topWizardLines = new String[0];

                        // If all 21 magic items have been brewed
                        if (brewedPotions.size() == 21) {
                            allPotionsBrewed = true; // Mark that all magic items are done

                            // Show congratulatory message for finishing all magic items
                            topWizardLines = new String[] {
                                    "You've brewed " + potionName + ".",
                                    brewedPotions.size() + " of 21 magic items collected.",
                                    "You've mastered them all!",
                                    " ",
                                    "One final mix awaits..."
                            };
                        } else {
                            // If not all magic items done yet, encourage user to keep mixing
                            topWizardLines = new String[] {
                                    "You've brewed " + potionName + ".",
                                    brewedPotions.size() + " of 21 magic items collected.",
                                    "Keep experimenting...",
                                    " ",
                                    "Curious what your magic item does?",
                                    "Click its tiny icon on the shelf!"
                            };
                        }
                        repaint();

                        ingredient1 = null; // Clear selected ingredient 1
                        ingredient2 = null; // Clear selected ingredient 2
                        updateIngredientButtons(); // Update ingredient buttons to show none selected
                    } else {
                        // If less than two ingredients are selected, print message to console
                        System.out.println("Please select two ingredients.");
                    }
                }
                // If all potions brewed but final mix is not done yet
                else if (!finalMixDone) {
                    finalMixDone = true; // Mark that final mix is done

                    // Load happy wizard image for final state
                    ImageIcon finalWizardIcon = ImageLoader.loadAndScaleImageIcon("/images/happy_wizard.png", 387, 589,
                            "Final Wizard");

                    // If final wizard image and label exist, update wizard picture
                    if (finalWizardIcon != null && wizardLabel != null) {
                        wizardLabel.setIcon(finalWizardIcon);
                    }

                    // Show final congratulation text from the wizard
                    topWizardLines = new String[] {
                            "Bravo, little alchemist!",
                            "Every recipe, mastered.",
                            "My shelf has never looked so full.",
                            "Should the winds of magic call again,",
                            "you'll always be welcome in my hut.",
                    };

                    repaint();
                } else {
                    // If final mix already done and button clicked again, just print message
                    System.out.println("You've already finished the final mix.");
                }
            });

            add(mixButton);
        }
    }

    /**
     * Adds a parchment image as decoration on the panel.
     */
    private void addParchment() {
        ImageIcon parchmentIcon = ImageLoader.loadAndScaleImageIcon("/images/pergament.png", 325, 702, "Parchment");
        if (parchmentIcon != null) {
            JLabel parchmentLabel = new JLabel(parchmentIcon);
            parchmentLabel.setBounds(-40, 10, 325, 702);
            add(parchmentLabel);
        }
    }

    /**
     * Adds a shelf image as decoration on the panel.
     */
    private void addShelf() {
        ImageIcon shelfIcon = ImageLoader.loadAndScaleImageIcon("/images/shelf.png", 523, 523, "Shelf");
        if (shelfIcon != null) {
            JLabel shelfLabel = new JLabel(shelfIcon);
            shelfLabel.setBounds(10, 580, 523, 523);
            add(shelfLabel);
            setComponentZOrder(shelfLabel, getComponentCount() - 1);
        }
    }

    /**
     * Adds a wizard image as decoration on the panel.
     */
    private void addWizard() {
        ImageIcon wizardIcon = ImageLoader.loadAndScaleImageIcon("/images/neutral_wizard.png", 387, 589, "Wizard");
        if (wizardIcon != null) {
            wizardLabel = new JLabel(wizardIcon);
            wizardLabel.setBounds(1100, 380, 387, 589);
            add(wizardLabel);
        }
    }

    /**
     * Sets an ingredient as selected.
     * 
     * The first ingredient is set to ingredient1,
     * the second to ingredient2.
     * If both are already selected, ignore the new input.
     * 
     * @param ingredient Name of the ingredient selected
     */
    private void setIngredient(String ingredient) {
        if (ingredient1 == null) {
            ingredient1 = ingredient; // Set first ingredient
        } else if (ingredient2 == null) {
            ingredient2 = ingredient; // Set second ingredient
        } else {
            System.out.println("Already selected 2 ingredients.");
        }
        updateIngredientButtons(); // Update button images based on selection
    }

    /**
     * Updates ingredient button icons to show if selected or not.
     */
    private void updateIngredientButtons() {
        eyeButton.setIcon(isSelected("Eye") ? eyeSelectedIcon : eyeIcon);
        crystalButton.setIcon(isSelected("Crystal") ? crystalSelectedIcon : crystalIcon);
        mushroomsButton.setIcon(isSelected("Mushrooms") ? mushroomsSelectedIcon : mushroomsIcon);
        waterButton.setIcon(isSelected("Water") ? waterSelectedIcon : waterIcon);
        fireButton.setIcon(isSelected("Fire") ? fireSelectedIcon : fireIcon);
        airButton.setIcon(isSelected("Air") ? airSelectedIcon : airIcon);
        earthButton.setIcon(isSelected("Earth") ? earthSelectedIcon : earthIcon);
    }

    /**
     * Checks if the given ingredient is currently selected.
     * 
     * @param ingredient The name of the ingredient to check
     * @return true if the ingredient matches either of the two selected
     *         ingredients, false otherwise
     */
    private boolean isSelected(String ingredient) {
        return ingredient.equals(ingredient1) || ingredient.equals(ingredient2);
    }

    /**
     * Shows the image of the combined potion result.
     * 
     * Removes the old result if present, adds a new label with the potion image.
     * 
     * @param imageName Name of the potion image
     */
    private void showResultImage(String imageName) {
        if (resultLabel != null) {
            remove(resultLabel); // Remove old result image
            resultLabel = null;
        }

        // Load potion image icon
        ImageIcon potionIcon = ImageLoader.loadAndScaleImageIcon("/results/" + imageName + ".png", 200, 200, imageName);
        if (potionIcon != null) {
            resultLabel = new JLabel(potionIcon); // Create label with image
            resultLabel.setBounds(760, 320, 200, 200); // Position label
            add(resultLabel);
            repaint(); // Refresh panel to show new image
        }
    }

    /**
     * Resets the mixing panel to its initial state.
     * 
     * This clears selected ingredients, removes the potion result image,
     * clears all mini potion icons from the shelf, resets brewing progress flags,
     * resets the wizard's displayed text and image,
     * and resets the sound button visibility.
     * 
     * Called typically when switching back to this panel to start fresh.
     */
    public void resetMixingPanel() {
        // Remove the result image if it exists
        if (resultLabel != null) {
            remove(resultLabel); // Remove the result label from the panel
            resultLabel = null; // Clear the reference
        }

        // Reset selected ingredients
        ingredient1 = null; // Clear first selected ingredient
        ingredient2 = null; // Clear second selected ingredient
        updateIngredientButtons(); // Update buttons to reflect no selection

        // Remove all mini potion icons from the shelf
        for (JLabel icon : potionShelfIcons) {
            remove(icon); // Remove each icon from the panel
        }
        potionShelfIcons.clear(); // Clear the list holding the icons
        brewedPotions.clear(); // Clear the list of brewed potions
        allPotionsBrewed = false; // Reset flag indicating all potions brewed
        finalMixDone = false; // Reset flag for the final mix completion

        // Reset wizard text to the starting welcome message
        topWizardLines = new String[] {
                "Welcome, young alchemist!",
                "You've stepped into ancient secrets.",
                "Here, herbs whisper,",
                "cauldrons bubble, and secrets brew.",
                "Combine two mystic ingredients...",
                "and let the magic begin."
        };

        // Reset the wizard image to the neutral wizard icon if wizard label exists
        if (wizardLabel != null) {
            ImageIcon neutralWizardIcon = ImageLoader.loadAndScaleImageIcon(
                    "/images/neutral_wizard.png", 387, 589, "Neutral Wizard");
            if (neutralWizardIcon != null) {
                wizardLabel.setIcon(neutralWizardIcon); // Set neutral wizard image
            }
        }

        // Reset sound button visibility if sound was off
        if (soundOffButton.isVisible()) {
            soundOffButton.setVisible(false); // Hide sound off button
            soundOnButton.setVisible(true); // Show sound on button
        }

        repaint(); // Redraw the panel to reflect all changes
    }

    /**
     * Adds a small icon of the brewed potion to the next free slot on the shelf.
     * 
     * This shows a mini picture representing the created potion.
     * If all 21 slots are full, no new icon is added.
     * 
     * When the icon is clicked, it shows the potion's description and updates
     * wizard image.
     * 
     * @param potionName The name of the potion to add to the shelf.
     */
    private void addPotionToShelf(String potionName) {
        // Check if the shelf is already full (max 21 icons)
        if (potionShelfIcons.size() >= 21) {
            System.out.println("All 21 slots are already filled."); // Print message if full
            return; // Stop adding more icons
        }

        // Load and scale the potion image to 48x48 pixels for the mini icon
        ImageIcon miniIcon = ImageLoader.loadAndScaleImageIcon(
                "/results/" + potionName + ".png", 48, 48, "Mini-" + potionName);

        if (miniIcon != null) {
            // Create a JLabel to hold the mini icon image
            JLabel iconLabel = new JLabel(miniIcon);

            int iconSize = 48; // Size of the mini icon
            int yStart = 730; // Starting vertical position of the shelf
            int rowHeight = 70; // Height distance between shelf rows

            // X center positions for the 7 columns on the shelf
            int[] colCenters = { 74, 140, 210, 275, 340, 408, 470 };

            int index = potionShelfIcons.size(); // Number of icons currently on the shelf
            int cols = 7; // Number of columns per row
            int row = index / cols; // Calculate which row to place the icon in
            int col = index % cols; // Calculate which column to place the icon in

            // Calculate X position: center the icon horizontally in the column
            int x = colCenters[col] - iconSize / 2;

            // Calculate Y position: center the icon vertically in the row space
            int y = yStart + row * rowHeight + (rowHeight - iconSize) / 2;

            // Set position and size of the icon label on the panel
            iconLabel.setBounds(x, y, iconSize, iconSize);

            // Add the icon label to the list of shelf icons
            potionShelfIcons.add(iconLabel);

            // Add the icon label to the panel so it is visible
            add(iconLabel);

            // Make sure the icon is drawn above the shelf background image
            setComponentZOrder(iconLabel, 0);

            // Add a mouse listener to the icon label to respond to clicks
            iconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // When clicked, get the potion description text lines
                    List<String> lines = PotionDescriptions.getDescription(potionName);

                    // Update wizard text to show potion description
                    topWizardLines = lines.toArray(new String[0]);

                    // Show the big result image for the clicked potion
                    showResultImage(potionName);

                    // Update wizard image to a talking wizard if possible
                    ImageIcon wizardIcon = ImageLoader.loadAndScaleImageIcon(
                            "/images/talking_wizard.png", 387, 589, "Wizard");
                    if (wizardLabel != null && wizardIcon != null) {
                        wizardLabel.setIcon(wizardIcon);
                    }

                    // Redraw panel to update changes
                    repaint();
                }
            });

            // Redraw panel to show new icon immediately
            repaint();
        }
    }

    /**
     * Helper method to create a JButton with a given icon, position, and click
     * listener.
     * 
     * @param icon     The ImageIcon to show on the button.
     * @param x        The x-position of the button on the panel.
     * @param y        The y-position of the button on the panel.
     * @param listener The ActionListener to handle button clicks.
     * @return The configured JButton.
     */
    private JButton createSoundButton(ImageIcon icon, int x, int y, java.awt.event.ActionListener listener) {
        JButton button = new JButton(icon); // Create button with icon
        button.setBounds(x, y, 48, 48); // Set button size and position
        button.setBorderPainted(false); // Remove button border
        button.setContentAreaFilled(false); // Make button background transparent
        button.setFocusPainted(false); // Remove focus painting
        button.addActionListener(listener); // Add the click listener
        return button; // Return the ready button
    }

    /**
     * Normalize two ingredient names alphabetically to make combinations comparable
     * This makes "Fire+Water" and "Water+Fire" the same, so they don’t count as two
     * different potions
     */
    private String normalizeCombination(String a, String b) {
        java.util.List<String> list = java.util.Arrays.asList(a, b);
        list.sort(String::compareTo); // sort ingredients alphabetically
        return list.get(0) + "+" + list.get(1); // return "a+b" in sorted order
    }
}
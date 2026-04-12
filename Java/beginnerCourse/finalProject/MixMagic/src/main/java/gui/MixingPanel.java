package gui;

import data.GameData;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.Ingredient;
import model.PotionResult;

public class MixingPanel extends JPanel {
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;
    private static final int RIGHT_MARGIN = 40;
    private static final int BOTTOM_MARGIN = 40;
    private static final int MAX_SELECTED_INGREDIENTS = 2;

    private static final int BASE_SCREEN_WIDTH = 1920;
    private static final int BASE_SCREEN_HEIGHT = 1080;

    private static final int SHELF_BASE_X = 58;
    private static final int SHELF_BASE_Y = 650;
    private static final int SHELF_BASE_WIDTH = 595;

    private static final int PARCHMENT_BASE_X = 10;
    private static final int PARCHMENT_BASE_Y = 50;
    private static final int PARCHMENT_BASE_HEIGHT = 750;

    private static final int RESULT_BASE_X = 830;
    private static final int RESULT_BASE_Y = 302;
    private static final int RESULT_BASE_WIDTH = 345;
    private static final int RESULT_BASE_HEIGHT = 345;
    private static final float[][] INGREDIENT_POSITIONS = {
            {0.50f, 0.18f},
            {0.50f, 0.38f},
            {0.50f, 0.58f},
            {0.68f, 0.40f},
            {0.18f, 0.66f},
            {0.50f, 0.66f},
            {0.82f, 0.66f}
    };

    private final PotionMixerWindow window;
    private final Image backgroundImage;
    private final Image shelfImage;
    private final Image pergamentImage;
    private final JButton backButton;
    private final JButton mixButton;
    private final List<Ingredient> ingredients;
    private final Map<String, PotionResult> potionResults;
    private final Map<String, PotionResult> recipes;
    private final List<Ingredient> selectedIngredients = new ArrayList<>();
    private final List<PotionResult> craftedResults = new ArrayList<>();
    private final List<Rectangle> ingredientHitboxes = new ArrayList<>();

    private PotionResult mixedResult;
    private Rectangle shelfBounds = new Rectangle();
    private Rectangle parchmentBounds = new Rectangle();
    private Rectangle resultBounds = new Rectangle();

    public MixingPanel(PotionMixerWindow window) {
        this.window = window;
        setLayout(null);

        ingredients = GameData.loadIngredients();
        potionResults = GameData.loadPotionResults();
        recipes = GameData.loadRecipes(potionResults);

        backButton = createButton("Back", 38f);
        backButton.addActionListener(e -> this.window.switchToMenuPanel());
        add(backButton);

        mixButton = createButton("Mix", 34f);
        mixButton.setEnabled(false);
        mixButton.addActionListener(e -> mixSelectedIngredients());
        add(mixButton);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleIngredientClick(e.getX(), e.getY());
            }
        });

        backgroundImage = loadImage("/enviroment/alchemyLabor.png");
        shelfImage = loadImage("/enviroment/shelf.png");
        pergamentImage = loadImage("/enviroment/pergament.png");
    }

    public void resetMixingPanel() {
        selectedIngredients.clear();
        craftedResults.clear();
        mixedResult = null;
        mixButton.setEnabled(false);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        updateScaledBounds();
        drawEnvironment(g2);
        drawIngredientIcons(g2);
        drawMixedResult(g2);
        drawShelfResults(g2);

        g2.dispose();
    }

    @Override
    public void doLayout() {
        super.doLayout();

        float scale = getScaleFactor();
        updateScaledBounds();
        int backWidth = Math.round(BUTTON_WIDTH * scale);
        int backHeight = Math.round(BUTTON_HEIGHT * scale);
        int rightMargin = Math.round(RIGHT_MARGIN * scale);
        int bottomMargin = Math.round(BOTTOM_MARGIN * scale);

        int backX = getWidth() - backWidth - rightMargin;
        int backY = getHeight() - backHeight - bottomMargin;
        backButton.setBounds(backX, backY, backWidth, backHeight);

        int mixWidth = Math.round(150 * scale);
        int mixHeight = Math.round(56 * scale);
        int mixX = (getWidth() - mixWidth) / 2;
        int mixY = getHeight() - mixHeight - Math.round(40 * scale);
        mixButton.setBounds(mixX, mixY, mixWidth, mixHeight);
        mixButton.setFont(AppFonts.alagard(34f * scale));
        backButton.setFont(AppFonts.alagard(34f * scale));
    }

    private JButton createButton(String text, float fontSize) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#F8C8DC"));
        button.setFont(AppFonts.alagard(fontSize));
        button.setFocusable(false);
        return button;
    }

    private void handleIngredientClick(int mouseX, int mouseY) {
        for (int i = 0; i < ingredientHitboxes.size(); i++) {
            Rectangle hitbox = ingredientHitboxes.get(i);
            if (!hitbox.contains(mouseX, mouseY)) {
                continue;
            }

            Ingredient ingredient = ingredients.get(i);
            toggleIngredientSelection(ingredient);
            return;
        }
    }

    private void toggleIngredientSelection(Ingredient ingredient) {
        if (selectedIngredients.contains(ingredient)) {
            selectedIngredients.remove(ingredient);
        } else {
            if (selectedIngredients.size() == MAX_SELECTED_INGREDIENTS) {
                selectedIngredients.remove(0);
            }
            selectedIngredients.add(ingredient);
        }

        mixedResult = null;
        mixButton.setEnabled(selectedIngredients.size() == MAX_SELECTED_INGREDIENTS);
        repaint();
    }

    private void mixSelectedIngredients() {
        if (selectedIngredients.size() != MAX_SELECTED_INGREDIENTS) {
            return;
        }

        String first = selectedIngredients.get(0).getName();
        String second = selectedIngredients.get(1).getName();
        mixedResult = recipes.get(createRecipeKey(first, second));

        if (mixedResult != null && !craftedResults.contains(mixedResult)) {
            craftedResults.add(mixedResult);
        }

        selectedIngredients.clear();
        mixButton.setEnabled(false);
        repaint();
    }

    private void updateScaledBounds() {
        float scale = getScaleFactor();

        shelfBounds = createScaledBoundsFromWidth(
                SHELF_BASE_X,
                SHELF_BASE_Y,
                SHELF_BASE_WIDTH,
                shelfImage,
                scale
        );
        parchmentBounds = createScaledBoundsFromHeight(
                PARCHMENT_BASE_X,
                PARCHMENT_BASE_Y,
                PARCHMENT_BASE_HEIGHT,
                pergamentImage,
                scale
        );
        resultBounds = createScaledBounds(
                RESULT_BASE_X,
                RESULT_BASE_Y,
                RESULT_BASE_WIDTH,
                RESULT_BASE_HEIGHT,
                scale
        );
    }

    private Rectangle createScaledBounds(int x, int y, int width, int height, float scale) {
        return new Rectangle(
                Math.round(x * scale),
                Math.round(y * scale),
                Math.round(width * scale),
                Math.round(height * scale)
        );
    }

    private Rectangle createScaledBoundsFromWidth(int x, int y, int width, Image image, float scale) {
        int scaledWidth = Math.round(width * scale);
        int scaledHeight = Math.round((scaledWidth / (float) image.getWidth(this)) * image.getHeight(this));
        return new Rectangle(
                Math.round(x * scale),
                Math.round(y * scale),
                scaledWidth,
                scaledHeight
        );
    }

    private Rectangle createScaledBoundsFromHeight(int x, int y, int height, Image image, float scale) {
        int scaledHeight = Math.round(height * scale);
        int scaledWidth = Math.round((scaledHeight / (float) image.getHeight(this)) * image.getWidth(this));
        return new Rectangle(
                Math.round(x * scale),
                Math.round(y * scale),
                scaledWidth,
                scaledHeight
        );
    }

    private void drawEnvironment(Graphics2D g2) {
        Object previousInterpolation = g2.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(pergamentImage, parchmentBounds.x, parchmentBounds.y, parchmentBounds.width, parchmentBounds.height, this);
        g2.drawImage(shelfImage, shelfBounds.x, shelfBounds.y, shelfBounds.width, shelfBounds.height, this);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, previousInterpolation);
    }

    private void drawIngredientIcons(Graphics2D g2) {
        ingredientHitboxes.clear();

        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            Rectangle drawBounds = createIngredientBounds(ingredient, i);
            ingredientHitboxes.add(drawBounds);

            Image image = getDisplayedIngredientIcon(ingredient).getImage();
            g2.drawImage(image, drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height, this);
        }
    }

    private Rectangle createIngredientBounds(Ingredient ingredient, int index) {
        ImageIcon imageIcon = getDisplayedIngredientIcon(ingredient);
        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();

        float[] position = INGREDIENT_POSITIONS[index];
        int centerX = parchmentBounds.x + Math.round(parchmentBounds.width * position[0]);
        int centerY = parchmentBounds.y + Math.round(parchmentBounds.height * position[1]);

        float widthFactor;
        float heightFactor;

        if (ingredient.getName().equals("Eye")
                || ingredient.getName().equals("Crystal")
                || ingredient.getName().equals("Mushrooms")) {
            widthFactor = 0.30f;
            heightFactor = 0.20f;
        } else {
            widthFactor = 0.18f;
            heightFactor = 0.12f;
        }

        int maxWidth = Math.round(parchmentBounds.width * widthFactor);
        int maxHeight = Math.round(parchmentBounds.height * heightFactor);

        float widthScale = maxWidth / (float) iconWidth;
        float heightScale = maxHeight / (float) iconHeight;
        float scale = Math.min(widthScale, heightScale);

        int drawWidth = Math.round(iconWidth * scale);
        int drawHeight = Math.round(iconHeight * scale);
        int drawX = centerX - (drawWidth / 2);
        int drawY = centerY - (drawHeight / 2);

        return new Rectangle(drawX, drawY, drawWidth, drawHeight);
    }

    private ImageIcon getDisplayedIngredientIcon(Ingredient ingredient) {
        if (selectedIngredients.contains(ingredient)) {
            return ingredient.getSelectedIcon();
        }
        return ingredient.getIcon();
    }

    private void drawMixedResult(Graphics2D g2) {
        if (mixedResult == null) {
            return;
        }

        ImageIcon resultIcon = mixedResult.getIcon();
        Rectangle drawBounds = fitIntoBounds(resultIcon, resultBounds, 0.95f);
        g2.drawImage(resultIcon.getImage(), drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height, this);
    }

    private void drawShelfResults(Graphics2D g2) {
        if (craftedResults.isEmpty()) {
            return;
        }

        int columns = 7;
        int rows = 3;
        int innerPaddingX = Math.round(shelfBounds.width * 0.06f);
        int innerPaddingTop = Math.round(shelfBounds.height * 0.11f);
        int innerPaddingBottom = Math.round(shelfBounds.height * 0.12f);

        int contentWidth = shelfBounds.width - (innerPaddingX * 2);
        int contentHeight = shelfBounds.height - innerPaddingTop - innerPaddingBottom;
        int cellWidth = contentWidth / columns;
        int cellHeight = contentHeight / rows;

        int maxVisibleResults = Math.min(craftedResults.size(), columns * rows);
        for (int i = 0; i < maxVisibleResults; i++) {
            PotionResult result = craftedResults.get(i);
            int row = i / columns;
            int column = i % columns;

            int cellX = shelfBounds.x + innerPaddingX + (column * cellWidth);
            int cellY = shelfBounds.y + innerPaddingTop + (row * cellHeight);

            Rectangle cellBounds = new Rectangle(cellX, cellY, cellWidth, cellHeight);
            Rectangle drawBounds = fitIntoBounds(result.getIcon(), cellBounds, 0.82f);
            g2.drawImage(result.getIcon().getImage(), drawBounds.x, drawBounds.y, drawBounds.width, drawBounds.height, this);
        }
    }

    private Rectangle fitIntoBounds(ImageIcon icon, Rectangle bounds, float fillRatio) {
        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();

        float widthScale = bounds.width / (float) iconWidth;
        float heightScale = bounds.height / (float) iconHeight;
        float scale = Math.min(widthScale, heightScale) * fillRatio;

        int drawWidth = Math.round(iconWidth * scale);
        int drawHeight = Math.round(iconHeight * scale);
        int drawX = bounds.x + (bounds.width - drawWidth) / 2;
        int drawY = bounds.y + (bounds.height - drawHeight) / 2;

        return new Rectangle(drawX, drawY, drawWidth, drawHeight);
    }

    private float getScaleFactor() {
        return Math.min(getWidth() / (float) BASE_SCREEN_WIDTH, getHeight() / (float) BASE_SCREEN_HEIGHT);
    }

    private Image loadImage(String resourcePath) {
        try {
            URL imageUrl = getClass().getResource(resourcePath);
            if (imageUrl == null) {
                throw new IllegalArgumentException("Ressource '" + resourcePath + "' wurde nicht gefunden.");
            }
            return ImageIO.read(imageUrl);
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Bild '" + resourcePath + "' konnte nicht geladen werden.", e);
        }
    }

    private String createRecipeKey(String first, String second) {
        if (first.compareTo(second) < 0) {
            return first + "|" + second;
        }
        return second + "|" + first;
    }
}

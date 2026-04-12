package model;

import java.util.List;

public class Recipe {
    private final List<String> ingredientNames;
    private final PotionResult result;

    public Recipe(List<String> ingredientNames, PotionResult result) {
        this.ingredientNames = List.copyOf(ingredientNames);
        this.result = result;
    }

    public List<String> getIngredientNames() {
        return ingredientNames;
    }

    public PotionResult getResult() {
        return result;
    }
}

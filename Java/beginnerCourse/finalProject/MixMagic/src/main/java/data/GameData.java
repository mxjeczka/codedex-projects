package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Ingredient;
import model.PotionResult;
import util.ImageLoader;

public final class GameData {
    private GameData() {
    }

    public static List<Ingredient> loadIngredients() {
        return List.of(
                createIngredient("Eye", "/ingredients/Eye.png", "/ingredients/Eye_selected.png", 121, 121),
                createIngredient("Crystal", "/ingredients/Crystal.png", "/ingredients/Crystal_selected.png", 121, 121),
                createIngredient("Mushrooms", "/ingredients/Mushrooms.png", "/ingredients/Mushrooms_selected.png", 121, 121),
                createIngredient("Water", "/ingredients/water.png", "/ingredients/water_selected.png", 90, 90),
                createIngredient("Fire", "/ingredients/fire.png", "/ingredients/fire_selected.png", 98, 98),
                createIngredient("Air", "/ingredients/air.png", "/ingredients/air_selected.png", 92, 92),
                createIngredient("Earth", "/ingredients/earth.png", "/ingredients/earth_selected.png", 96, 96)
        );
    }

    public static Map<String, PotionResult> loadPotionResults() {
        return Map.ofEntries(
                Map.entry("Aqua Dragon", createPotionResult("Aqua Dragon", "/results/Aqua Dragon.png")),
                Map.entry("Ashcap Beast", createPotionResult("Ashcap Beast", "/results/Ashcap Beast.png")),
                Map.entry("Cloud Serpent", createPotionResult("Cloud Serpent", "/results/Cloud Serpent.png")),
                Map.entry("Dust Collossus", createPotionResult("Dust Collossus", "/results/Dust Collossus.png")),
                Map.entry("Flame Shard Golem", createPotionResult("Flame Shard Golem", "/results/Flame Shard Golem.png")),
                Map.entry("Fungus Ent", createPotionResult("Fungus Ent", "/results/Fungus Ent.png")),
                Map.entry("Gale Prism", createPotionResult("Gale Prism", "/results/Gale Prism.png")),
                Map.entry("Geo-Shard Beast", createPotionResult("Geo-Shard Beast", "/results/Geo-Shard Beast.png")),
                Map.entry("Golem's Eye Totem", createPotionResult("Golem's Eye Totem", "/results/Golem's Eye Totem.png")),
                Map.entry("Lava Monster", createPotionResult("Lava Monster", "/results/Lava Monster.png")),
                Map.entry("Moon Elf", createPotionResult("Moon Elf", "/results/moonElf.png")),
                Map.entry("Mossbeast", createPotionResult("Mossbeast", "/results/Mossbeast.png")),
                Map.entry("MushKelp Serpent", createPotionResult("MushKelp Serpent", "/results/MushKelp Serpent.png")),
                Map.entry("Oracle Eye", createPotionResult("Oracle Eye", "/results/Oracle Eye.png")),
                Map.entry("Phoenix", createPotionResult("Phoenix", "/results/Phoenix.png")),
                Map.entry("Rootcore Crystal", createPotionResult("Rootcore Crystal", "/results/Rootcore Crystal.png")),
                Map.entry("Sea-Sight Jellyfish", createPotionResult("Sea-Sight Jellyfish", "/results/Sea-Sight Jellyfish.png")),
                Map.entry("Spore Spirit", createPotionResult("Spore Spirit", "/results/Spore Spirit.png")),
                Map.entry("Steam Spirit", createPotionResult("Steam Spirit", "/results/Steam Spirit.png")),
                Map.entry("Storm Salamander", createPotionResult("Storm Salamander", "/results/stormSalamander.png")),
                Map.entry("Swirl of Embers", createPotionResult("Swirl of Embers", "/results/Swirl of Embers.png")),
                Map.entry("Umbra Wraith", createPotionResult("Umbra Wraith", "/results/umbraWraith.png")),
                Map.entry("Vine Monster", createPotionResult("Vine Monster", "/results/vineMonster.png")),
                Map.entry("Weaker Dreams", createPotionResult("Weaker Dreams", "/results/WeakerDreams.png")),
                Map.entry("Wind Butterfly", createPotionResult("Wind Butterfly", "/results/WindButterfly.png"))
        );
    }

    public static Map<String, PotionResult> loadRecipes(Map<String, PotionResult> results) {
        Map<String, PotionResult> recipes = new HashMap<>();

        recipes.put(createRecipeKey("Eye", "Crystal"), results.get("Oracle Eye"));
        recipes.put(createRecipeKey("Eye", "Mushrooms"), results.get("Weaker Dreams"));
        recipes.put(createRecipeKey("Eye", "Water"), results.get("Sea-Sight Jellyfish"));
        recipes.put(createRecipeKey("Eye", "Fire"), results.get("Phoenix"));
        recipes.put(createRecipeKey("Eye", "Air"), results.get("Wind Butterfly"));
        recipes.put(createRecipeKey("Eye", "Earth"), results.get("Golem's Eye Totem"));

        recipes.put(createRecipeKey("Crystal", "Mushrooms"), results.get("Rootcore Crystal"));
        recipes.put(createRecipeKey("Crystal", "Water"), results.get("Aqua Dragon"));
        recipes.put(createRecipeKey("Crystal", "Fire"), results.get("Flame Shard Golem"));
        recipes.put(createRecipeKey("Crystal", "Air"), results.get("Gale Prism"));
        recipes.put(createRecipeKey("Crystal", "Earth"), results.get("Geo-Shard Beast"));

        recipes.put(createRecipeKey("Mushrooms", "Water"), results.get("MushKelp Serpent"));
        recipes.put(createRecipeKey("Mushrooms", "Fire"), results.get("Ashcap Beast"));
        recipes.put(createRecipeKey("Mushrooms", "Air"), results.get("Spore Spirit"));
        recipes.put(createRecipeKey("Mushrooms", "Earth"), results.get("Fungus Ent"));

        recipes.put(createRecipeKey("Water", "Fire"), results.get("Steam Spirit"));
        recipes.put(createRecipeKey("Water", "Air"), results.get("Cloud Serpent"));
        recipes.put(createRecipeKey("Water", "Earth"), results.get("Mossbeast"));

        recipes.put(createRecipeKey("Fire", "Air"), results.get("Swirl of Embers"));
        recipes.put(createRecipeKey("Fire", "Earth"), results.get("Lava Monster"));

        recipes.put(createRecipeKey("Air", "Earth"), results.get("Dust Collossus"));

        return recipes;
    }

    private static String createRecipeKey(String first, String second) {
        if (first.compareTo(second) < 0) {
            return first + "|" + second;
        }
        return second + "|" + first;
    }

    private static Ingredient createIngredient(String name, String iconPath, String selectedIconPath, int width, int height) {
        return new Ingredient(
                name,
                ImageLoader.loadAndScaleImageIcon(iconPath, width, height, name),
                ImageLoader.loadAndScaleImageIcon(selectedIconPath, width, height, name + " Selected")
        );
    }

    private static PotionResult createPotionResult(String name, String iconPath) {
        return new PotionResult(
                name,
                name,
                ImageLoader.loadAndScaleImageIcon(iconPath, 220, 220, name)
        );
    }
}

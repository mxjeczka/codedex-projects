package logic;

/**
 * This class checks for specific combinations of ingredients and returns
 * a potion name based on those combinations.
 * 
 * @author Maja Mercedes Perz
 */
public class PotionCombiner {

    /**
     * Combines two ingredients and returns the name of the resulting potion.
     *
     * @param i1 the name of the first ingredient
     * @param i2 the name of the second ingredient
     * @return the name of the potion if the combination is known,
     *         otherwise "unknown_potion"
     */
    public static String combine(String i1, String i2) {

        if ((i1.equals("Eye") && i2.equals("Crystal")) || (i1.equals("Crystal") && i2.equals("Eye"))) {
            return "Oracle Eye";
        }

        if ((i1.equals("Eye") && i2.equals("Air")) || (i1.equals("Air") && i2.equals("Eye"))) {
            return "WindButterfly";
        }

        if ((i1.equals("Eye") && i2.equals("Water")) || (i1.equals("Water") && i2.equals("Eye"))) {
            return "Sea-Sight Jellyfish";
        }

        if ((i1.equals("Eye") && i2.equals("Fire")) || (i1.equals("Fire") && i2.equals("Eye"))) {
            return "Phoenix";
        }

        if ((i1.equals("Eye") && i2.equals("Earth")) || (i1.equals("Earth") && i2.equals("Eye"))) {
            return "Golem's Eye Totem";
        }

        if ((i1.equals("Mushrooms") && i2.equals("Eye")) || (i1.equals("Eye") && i2.equals("Mushrooms"))) {
            return "WeakerDreams";
        }

        if ((i1.equals("Mushrooms") && i2.equals("Air")) || (i1.equals("Air") && i2.equals("Mushrooms"))) {
            return "Spore Spirit";
        }

        if ((i1.equals("Mushrooms") && i2.equals("Water")) || (i1.equals("Water") && i2.equals("Mushrooms"))) {
            return "MushKelp Serpent";
        }

        if ((i1.equals("Mushrooms") && i2.equals("Fire")) || (i1.equals("Fire") && i2.equals("Mushrooms"))) {
            return "Ashcap Beast";
        }

        if ((i1.equals("Mushrooms") && i2.equals("Earth")) || (i1.equals("Earth") && i2.equals("Mushrooms"))) {
            return "Fungus Ent";
        }

        if ((i1.equals("Mushrooms") && i2.equals("Crystal")) || (i1.equals("Crystal") && i2.equals("Mushrooms"))) {
            return "Rootcore Crystal";
        }

        if ((i1.equals("Crystal") && i2.equals("Air")) || (i1.equals("Air") && i2.equals("Crystal"))) {
            return "Gale Prism";
        }

        if ((i1.equals("Crystal") && i2.equals("Water")) || (i1.equals("Water") && i2.equals("Crystal"))) {
            return "Aqua Dragon";
        }

        if ((i1.equals("Crystal") && i2.equals("Fire")) || (i1.equals("Fire") && i2.equals("Crystal"))) {
            return "Flame Shard Golem";
        }

        if ((i1.equals("Crystal") && i2.equals("Earth")) || (i1.equals("Earth") && i2.equals("Crystal"))) {
            return "Geo-Shard Beast";
        }

        if ((i1.equals("Water") && i2.equals("Earth")) || (i1.equals("Earth") && i2.equals("Water"))) {
            return "Mossbeast";
        }

        if ((i1.equals("Water") && i2.equals("Fire")) || (i1.equals("Fire") && i2.equals("Water"))) {
            return "Steam Spirit";
        }

        if ((i1.equals("Water") && i2.equals("Air")) || (i1.equals("Air") && i2.equals("Water"))) {
            return "Cloud Serpent";
        }

        if ((i1.equals("Air") && i2.equals("Earth")) || (i1.equals("Earth") && i2.equals("Air"))) {
            return "Dust Collossus";
        }

        if ((i1.equals("Fire") && i2.equals("Earth")) || (i1.equals("Earth") && i2.equals("Fire"))) {
            return "Lava Monster";
        }

        if ((i1.equals("Fire") && i2.equals("Air")) || (i1.equals("Air") && i2.equals("Fire"))) {
            return "Swirl of Embers";
        }

        // If the ingredients do not match any known combination,
        // return a default name
        return "unknown_potion";
    }
}

package logic;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class stores a description for each magical potion creature.
 * It lets other classes get the description by using the creature's name.
 * 
 * @author Maja Mercedes Perz
 */
public class PotionDescriptions {

    // A map that connects a creature name (String) to its description (List of
    // Strings)
    private static final Map<String, List<String>> descriptions = new HashMap<>();

    // This block runs once when the class loads – it fills the map with data
    static {
        // Each entry adds the name and a list of lines for the description

        descriptions.put("Aqua Dragon", Arrays.asList(
                "Aqua Dragon:",
                " ",
                "Summons healing water.",
                "Its presence slowly heals wounds",
                "and refreshes energy.")); // Heals and refreshes – a helpful creature

        descriptions.put("Ashcap Beast", Arrays.asList(
                "Ashcap Beast:",
                " ",
                "Creates smoke screens.",
                "Used to escape danger or confuse",
                "enemies in battle.")); // Used for escape and trickery

        descriptions.put("Cloud Serpent", Arrays.asList(
                "Cloud Serpent:",
                " ",
                "Controls weather: brings rain or fog.",
                "Used to call storms or",
                "hide places in mist.")); // Weather-controlling creature

        descriptions.put("Dust Collossus", Arrays.asList(
                "Dust Collossus:",
                " ",
                "Forms walls or barriers from sand.",
                "Used for defense or",
                "distraction in battle.")); // Makes magical walls

        descriptions.put("Flame Shard Golem", Arrays.asList(
                "Flame Shard Golem:",
                " ",
                "Protective creature.",
                "Acts as a small magical guard that",
                "defends its owner with fire bursts.")); // A fire defender

        descriptions.put("Fungus Ent", Arrays.asList(
                "Fungus Ent:",
                " ",
                "Grows protective forests",
                "and heals nature. ",
                "Used to restore dead land or",
                "help plants grow fast.")); // Nature healer

        descriptions.put("Gale Prism", Arrays.asList(
                "Gale Prism:",
                " ",
                "Can store and release",
                "strong gusts of wind.",
                "Used to power wind-based machines",
                "or clear away fog and gas.")); // Wind-power item

        descriptions.put("Geo-Shard Beast", Arrays.asList(
                "Geo-Shard Beast:",
                " ",
                "Can smash through stone",
                "and dig tunnels.",
                "Used for mining or breaking",
                "through walls magically.")); // Earth-moving creature

        descriptions.put("Golem's Eye Totem", Arrays.asList(
                "Golem's Eye Totem:",
                " ",
                "Watches over an area and",
                "warns of danger.",
                "Used for protection in magical homes",
                "or gardens.")); // Magic alarm

        descriptions.put("Lava Monster", Arrays.asList(
                "Lava Monster:",
                " ",
                "Destroys obstacles and burns enemies.",
                "Very strong but hard to control.")); // Destructive fire creature

        descriptions.put("Mossbeast", Arrays.asList(
                "Mossbeast:",
                " ",
                "Makes plants grow fast and",
                "enriches soil.",
                "Used in farming or nature spells.")); // Growth helper

        descriptions.put("MushKelp Serpent", Arrays.asList(
                "MushKelp Serpent:",
                " ",
                "Cleanses poisoned or dirty water.",
                "Also releases spores that calm",
                "wild aquatic beasts.")); // Cleans water and calms fish

        descriptions.put("Oracle Eye", Arrays.asList(
                "Oracle Eye:",
                " ",
                "Answers questions through visions.",
                "Used in prophecy or",
                "decision-making rituals.")); // Used to see the future

        descriptions.put("Phoenix", Arrays.asList(
                "Phoenix:",
                " ",
                "Shows visions through flame.",
                "Can reveal future hints when",
                "burned in a ritual fire.")); // Fire and prophecy creature

        descriptions.put("Rootcore Crystal", Arrays.asList(
                "Rootcore Crystal:",
                " ",
                "Grows magical vines that",
                "protect an area.",
                "Used for defense or plant-based traps.")); // Nature defense

        descriptions.put("Sea-Sight Jellyfish", Arrays.asList(
                "Sea-Sight Jellyfish:",
                " ",
                "Allows deep-sea vision and ",
                "can detect magical objects underwater.",
                "Used by sea explorers and diviners.")); // Helps see underwater magic

        descriptions.put("Spore Spirit", Arrays.asList(
                "Spore Spirit:",
                " ",
                "Spreads sleeping spores.",
                "Useful for calming enemies or ",
                "putting creatures to sleep.")); // Sleep spell support

        descriptions.put("Steam Spirit", Arrays.asList(
                "Steam Spirit:",
                " ",
                "Boils enemies or cleanses areas.",
                "Used to burn toxins or ",
                "heat magical machines.")); // Hot steam for damage or cleaning

        descriptions.put("Swirl of Embers", Arrays.asList(
                "Swirl of Embers:",
                " ",
                "Unstable energy source.",
                "Powers flying devices or causes",
                "random fire/lightning explosions.")); // Wild magic energy

        descriptions.put("WeakerDreams", Arrays.asList(
                "WeakerDreams:",
                " ",
                "Creates dream illusions.",
                "Used in mental magic or to enter",
                "the dreams of others.")); // Dream-based spell creature

        descriptions.put("WindButterfly", Arrays.asList(
                "WindButterfly",
                " ",
                "Used to bend light and",
                "reveal hidden things.",
                "Its wings help uncover illusions",
                "or invisible objects.")); // Light-revealing flying insect
    }

    /**
     * Gets the description of a potion creature by name.
     * If the name is unknown, returns a default message.
     *
     * @param potionName The name of the potion creature
     * @return A list of lines that describe the creature
     */
    public static List<String> getDescription(String potionName) {
        // Get the description for the name, or return a default message if not found
        return descriptions.getOrDefault(potionName, Collections.singletonList("No description available."));
    }
}
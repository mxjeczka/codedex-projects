package logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PotionCombinerTest {

    @Test
    void testKnownCombinations() {
        // Test known ingredient combinations, order does not matter
        assertEquals("Oracle Eye", PotionCombiner.combine("Eye", "Crystal"));
        assertEquals("Oracle Eye", PotionCombiner.combine("Crystal", "Eye"));
        assertEquals("WindButterfly", PotionCombiner.combine("Eye", "Air"));
        assertEquals("WindButterfly", PotionCombiner.combine("Air", "Eye"));
        assertEquals("Mossbeast", PotionCombiner.combine("Water", "Earth"));
        assertEquals("Mossbeast", PotionCombiner.combine("Earth", "Water"));
    }

    @Test
    void testUnknownCombination() {
        // Test combination that is not known
        assertEquals("unknown_potion", PotionCombiner.combine("Eye", "UnknownIngredient"));
        assertEquals("unknown_potion", PotionCombiner.combine("Fire", "Dragon"));
    }
}

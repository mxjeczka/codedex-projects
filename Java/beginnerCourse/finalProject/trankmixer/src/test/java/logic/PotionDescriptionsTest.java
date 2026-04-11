package logic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PotionDescriptionsTest {

    @Test
    void testKnownDescription() {
        // Get description for a known potion creature
        List<String> description = PotionDescriptions.getDescription("Phoenix");

        // Check that description is not empty
        assertTrue(description.size() > 0, "Description should not be empty");

        // Check that the first line contains the potion's name
        assertEquals("Phoenix:", description.get(0));
    }

    @Test
    void testUnknownDescription() {
        // Get description for a potion name that does not exist
        List<String> description = PotionDescriptions.getDescription("UnknownCreature");

        // It should return the default message
        assertEquals(1, description.size());
        assertEquals("No description available.", description.get(0));
    }
}
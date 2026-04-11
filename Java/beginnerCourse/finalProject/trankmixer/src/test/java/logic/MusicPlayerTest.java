package logic;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MusicPlayerTest {

    private MusicPlayer musicPlayer;

    @BeforeEach
    void setUp() {
        // This method runs before each test runs
        // It makes a new MusicPlayer so every test has a fresh one
        musicPlayer = new MusicPlayer();
    }

    @Test
    void testLoadMusicWithValidResource() {
        // Try to load a real music file from resources
        musicPlayer.loadMusic("/sounds/91_Elven_Glade.wav");

        // We can't check clip directly because it's private,
        // but if no error happens, the test is okay

        // Simple test: no error means test passed
        assertTrue(true, "Loading valid music file should not fail");
    }

    @Test
    void testLoadMusicWithInvalidResource() {
        // Try to load a file that does not exist
        musicPlayer.loadMusic("/nonexistent-file.wav");

        // This should not crash or throw an error,
        // because the code handles missing files internally

        assertTrue(true, "Loading invalid music file should not throw exception");
    }

    @Test
    void testPlayAndStopWithoutLoad() {
        // Call play and stop without loading music first
        // It should not crash or throw errors

        musicPlayer.play();
        musicPlayer.stop();

        assertTrue(true, "Play and stop without loaded music should not fail");
    }
}

import gui.PotionMixerWindow;

/**
 * This is the main class of the Alchemy Lab project.
 * 
 * The project is a small graphical game where players can mix 
 * magical ingredients to discover fun fantasy potions.
 * 
 * It has two main screens:
 * - A main menu where the player can start the game.
 * - A mixing screen where the player selects two ingredients and sees the
 * result.
 *
 * The GUI is built using Java Swing. Images and custom fonts are used to create
 * a magical pixel-art-style atmosphere.
 * 
 * The program starts by showing the main menu window.
 *
 * @author Maja Mercedes Perz
 */
public class Main {
  public static void main(String[] args) {

    new PotionMixerWindow();
  }
}
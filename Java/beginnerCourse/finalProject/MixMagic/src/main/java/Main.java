import javax.swing.SwingUtilities;
import gui.PotionMixerWindow;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PotionMixerWindow::new);
    }
}
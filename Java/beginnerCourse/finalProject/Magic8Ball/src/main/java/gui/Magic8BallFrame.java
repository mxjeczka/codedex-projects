package gui;

import javax.swing.*;
import java.awt.*;

public class Magic8BallFrame extends JFrame {

    public Magic8BallFrame() {
        setTitle("Magic 8 Ball of Destiny");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new BackgroundPanel());
        setMinimumSize(new Dimension(900, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}

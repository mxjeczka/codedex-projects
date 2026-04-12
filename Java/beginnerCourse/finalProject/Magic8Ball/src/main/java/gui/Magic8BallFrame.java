package gui;

import javax.swing.*;

public class Magic8BallFrame extends JFrame {

    public Magic8BallFrame() {
        setTitle("Magic 8 Ball of Destiny");
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1536, 1024);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new BackgroundPanel());
        setVisible(true);
    }
}

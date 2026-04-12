package gui;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PotionMixerWindow extends JFrame {
    private final MenuPanel menuPanel;
    private final MixingPanel mixingPanel;

    public PotionMixerWindow() {
        setTitle("Welcome to the magic World");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Rectangle usableBounds = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();

        setBounds(usableBounds);
        setMaximizedBounds(usableBounds);

        menuPanel = new MenuPanel(this);
        mixingPanel = new MixingPanel(this);

        setContentPane(menuPanel);

//        addWindowStateListener(e -> {
//            int newState = e.getNewState();
//            boolean minimized = (newState & JFrame.ICONIFIED) == JFrame.ICONIFIED;
//            boolean maximized = (newState & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH;
//
//            if (!minimized && !maximized) {
//                SwingUtilities.invokeLater(() -> {
//                    setState(JFrame.NORMAL);
//                    setExtendedState(JFrame.MAXIMIZED_BOTH);
//                    toFront();
//                });
//            }
//        });

        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void switchToMixingPanel() {
        setContentPane(mixingPanel);
        revalidate();
        repaint();
    }

    public void switchToMenuPanel() {
        mixingPanel.resetMixingPanel();
        setContentPane(menuPanel);
        revalidate();
        repaint();
    }
}

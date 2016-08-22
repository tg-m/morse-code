package code.morse.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? " +
                SwingUtilities.isEventDispatchThread());
        final JFrame f = new JFrame("Morse Code Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new SomeDemoPanelFoundInTheWeb());
        f.pack();
        f.setVisible(true);
    }

}

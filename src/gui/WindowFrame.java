/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author patrick
 */
public final class WindowFrame extends JFrame {

    private JPanel currentPanel;

    public WindowFrame() {
        super();

        displayScreen(new StartScreen(this));
        

        setSize(1024, 768);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void displayScreen(JPanel screen) {

        if (currentPanel != null)
            remove(currentPanel);

        add(screen);
        currentPanel = screen;
        validate();
    }
}

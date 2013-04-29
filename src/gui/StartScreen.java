/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import brawllogic.ComputerPlayer;
import brawllogic.Controller;
import brawllogic.Fighter;
import brawllogic.GameState;
import brawllogic.Player;
import brawllogic.TournamentModeGameState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author patrick
 */
public class StartScreen extends JPanel {

    private WindowFrame frame;
    private JButton startButton;

    public StartScreen(WindowFrame frame) {
        super();

        this.frame = frame;
        frame.setTitle("Start Screen");
        startButton = new JButton("Start game");
        startButton.addActionListener(new StartButtonListener());
        add(startButton);
    }

    private class StartButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Fighter bennett = new Fighter("Original", "Bennett");
            Fighter darwin = new Fighter("Original", "Darwin");

            GameState gameState = new TournamentModeGameState(bennett, darwin);

            // Set up the players
            ComputerPlayer leftPlayer = new ComputerPlayer(Player.LEFT, gameState, 1.5);
            ComputerPlayer rightPlayer = new ComputerPlayer(Player.RIGHT, gameState, 2);

            // Create a controller that receives input for the left player
            Controller controller = new Controller(gameState);

            // Create a game panel
            GamePanel panel = new GamePanel(frame, controller);

            panel.start();
            leftPlayer.start();
            rightPlayer.start();
            
            frame.displayScreen(panel);
        }

    }
}

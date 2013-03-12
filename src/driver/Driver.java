package driver;

import brawllogic.ComputerPlayer;
import brawllogic.Controller;
import brawllogic.Fighter;
import brawllogic.GameState;
import brawllogic.HumanPlayer;
import brawllogic.KeyMap;
import brawllogic.Player;
import brawllogic.TournamentModeGameState;
import consoleui.ConsoleUI;
import gui.GamePanel;
import javax.swing.JFrame;

/**
 * @author Patrick Herrmann
 */
public class Driver {
    
    public static void main(String[] args) {
        Fighter bennett = new Fighter("Original", "Bennett");
        Fighter darwin = new Fighter("Original", "Darwin");
        
        GameState gameState = new TournamentModeGameState(bennett, darwin);

        // Set up the players
        HumanPlayer leftPlayer = new HumanPlayer(Player.LEFT, KeyMap.getDefaultKeyMap(Player.LEFT));
        ComputerPlayer rightPlayer = new ComputerPlayer(Player.RIGHT, gameState, 1.0);

        // Create a controller that receives input for the left player
        Controller controller = new Controller(gameState, leftPlayer);

        // Create a game panel and a console UI as two views
        GamePanel panel = new GamePanel(controller, 1280, 854);
        ConsoleUI cui = new ConsoleUI(controller);

        // Add the game panel to a JFrame
        JFrame frame = new JFrame("Brawl");
        frame.add(panel);
        frame.addKeyListener(panel.getKeyListener());
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Kick everything off
        rightPlayer.start();
        panel.start();
        cui.start();
    }
    
}

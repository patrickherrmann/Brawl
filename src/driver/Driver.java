package driver;

import brawllogic.Controller;
import brawllogic.Fighter;
import brawllogic.GameState;
import brawllogic.KeyMap;
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

        Controller controller = new Controller(gameState, KeyMap.getDefaultKeyMap());
        
        GamePanel panel = new GamePanel(controller, 1280, 854);
        ConsoleUI cui = new ConsoleUI(controller);
        
        JFrame frame = new JFrame("Brawl");
        frame.add(panel);
        frame.addKeyListener(panel.getKeyListener());
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.start();
        cui.start();
    }
    
}

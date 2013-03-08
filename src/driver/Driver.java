package driver;

import brawllogic.Controller;
import brawllogic.Fighter;
import brawllogic.GameState;
import brawllogic.KeyMap;
import brawllogic.TournamentModeGameState;
import consoleui.ConsoleUI;
import gui.GameFrame;

/**
 * @author Patrick Herrmann
 */
public class Driver {
    
    public static void main(String[] args) {
        Fighter bennett = new Fighter("Original", "Bennett");
        Fighter darwin = new Fighter("Original", "Darwin");
        
        GameState gameState = new TournamentModeGameState(bennett, darwin);

        Controller controller = new Controller(gameState, KeyMap.getDefaultKeyMap());
        
        GameFrame gui = new GameFrame(controller, 1280, 854);
        ConsoleUI cui = new ConsoleUI(controller);
        gui.start();
        cui.start();
    }
    
}

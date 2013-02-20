package driver;

import brawllogic.BrawlGame;
import brawllogic.Fighter;
import brawllogic.TournamentModeGame;
import consoleui.ConsoleUI;
import gui.GameFrame;

/**
 * @author Patrick Herrmann
 */
public class Driver {
    
    public static void main(String[] args) {
        Fighter bennett = new Fighter("Original", "Bennett");
        Fighter darwin = new Fighter("Original", "Darwin");
        
        BrawlGame game = new TournamentModeGame(bennett, darwin);
        
        GameFrame gui = new GameFrame(game, 1280, 854);
        gui.setVisible(true);
        ConsoleUI cui = new ConsoleUI(game);
        cui.start();
    }
    
}

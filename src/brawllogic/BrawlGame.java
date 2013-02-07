package brawllogic;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Patrick Herrmann
 */
public abstract class BrawlGame {
    
    protected GameState state;
    
    protected BrawlGame(Fighter leftFighter, Fighter rightFighter) {
        
        if (leftFighter == null)
            throw new IllegalArgumentException("leftFighter cannot be null.");
        
        if (rightFighter == null)
            throw new IllegalArgumentException("rightFighter cannot be null.");
        
        Map<Player, Fighter> fighters = new EnumMap(Player.class);
        
        fighters.put(Player.LEFT, leftFighter);
        fighters.put(Player.RIGHT, rightFighter);
        
        state = new GameState(fighters);
    }
    
    public GameState getState() {
        return state;
    }
    
    public abstract void tryMove(Player player, BasePosition basePosition, Player side) throws IllegalMoveException;
    
    public abstract void draw(Player player) throws IllegalMoveException;
    
    public abstract Player getCurrentPlayer();
}

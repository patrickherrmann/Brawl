package brawllogic;

/**
 * @author Patrick Herrmann
 */
public enum Player {
    LEFT, RIGHT;
    
    public Player getOpponent() {
        return this == LEFT ? RIGHT : LEFT;
    }
}

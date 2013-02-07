package brawllogic;

/**
 * @author Patrick Herrmann
 */
public final class IllegalMoveException extends Exception {
    
    private Player player;
    
    public IllegalMoveException(String message, Player player) {
        super(message);
        
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }
}

package brawllogic;

import java.util.Random;

/**
 * @author Patrick Herrmann
 */
public final class TrainingModeGame extends BrawlGame {
    
    private Player currentPlayer;
    private boolean hasDrawn = false;
    
    public TrainingModeGame(Fighter leftFighter, Fighter rightFighter) {
        super(leftFighter, rightFighter);
        
        currentPlayer = new Random().nextBoolean() ? Player.LEFT : Player.RIGHT;
    }
    
    @Override
    public void tryMove(Player player, BasePosition basePosition, Player side) throws IllegalMoveException {
        
        if (player != currentPlayer)
            throw new IllegalMoveException("It's not your turn!", player);
        
        state.tryMove(player, basePosition, side);
        
        currentPlayer = currentPlayer.getOpponent();
        hasDrawn = false;
    }
    
    @Override
    public void draw(Player player) throws IllegalMoveException {
        
        if (player != currentPlayer)
            throw new IllegalMoveException("You can't draw on your opponent's turn!", player);
        
        if (hasDrawn) {
            
            currentPlayer = currentPlayer.getOpponent();
            hasDrawn = false;
            
        } else {
        
            state.draw(player);
            
            hasDrawn = true;
        }
    }
    
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}

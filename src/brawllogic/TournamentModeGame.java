package brawllogic;

/**
 * @author Patrick Herrmann
 */
public class TournamentModeGame extends BrawlGame {
    
    public TournamentModeGame(Fighter leftFighter, Fighter rightFighter) {
        super(leftFighter, rightFighter);
    }
    
    @Override
    public void tryMove(Player player, BasePosition basePosition, Player side) throws IllegalMoveException {
        state.tryMove(player, basePosition, side);
    }

    @Override
    public void draw(Player player) throws IllegalMoveException {
        state.draw(player);
    }
    
    @Override
    public Player getCurrentPlayer() {
        return null;
    }
}

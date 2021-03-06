/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brawllogic;

import java.util.List;

/**
 *
 * @author patrick
 */
public class PlayCardAction extends Move {

    private Player side;
    private BasePosition basePosition;

    public PlayCardAction(Player player, Player side, BasePosition basePosition) {
        super(player);
        this.side = side;
        this.basePosition = basePosition;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getSide() {
        return side;
    }

    public BasePosition getBasePosition() {
        return basePosition;
    }

    @Override
    public int getHeuristic(GameState gameState) {

        List<Card> discard = gameState.getDiscard(player);
        
        if (discard.isEmpty())
            return -1;
        
        Card activeCard = discard.get(discard.size() - 1);
        List<Base> bases = gameState.getBases();
        int baseIndex = basePosition.getIndex(bases.size());
        
        if (baseIndex == -1)
            return activeCard.getType() == CardType.BASE ? 3 : -1;
        
        Base base = bases.get(baseIndex);

        switch (activeCard.getType()) {
            case HIT:
            case SMASH:
            case PRESS:
                return player == side ? 2 : -1;
            case BLOCK:
                return player == side ? -1 : 2;
            case CLEAR:
                return player == base.getWinner() ? -1 : 2;
            case FREEZE:
                return player == base.getWinner() ? 3 : -1;
            case BASE:
            default:
                return 1;
        }
    }

    @Override
    public void perform(GameState gameState) {
        gameState.move(this);
    }

    @Override
    public MoveAnalysis analyze(GameState gameState) {
        return gameState.analyzeMove(this);
    }
}

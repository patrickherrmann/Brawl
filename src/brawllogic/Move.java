/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brawllogic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patrick
 */
public class Move {

    public static List<Move> getAllMoves(Player player) {
        List<Move> moves = new ArrayList<Move>();

        for (Player side : Player.values()) {
            for (BasePosition basePosition : BasePosition.values()) {
                moves.add(new Move(player, side, basePosition));
            }
        }

        return moves;
    }
    private Player player;
    private Player side;
    private BasePosition basePosition;

    public Move(Player player, Player side, BasePosition basePosition) {
        this.player = player;
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

    public int getHeuristic(GameState gameState) {

        List<Card> discard = gameState.getDiscard(player);
        
        if (discard.isEmpty())
            return -1;

        Card activeCard = discard.get(discard.size() - 1);

        switch (activeCard.getType()) {
            case HIT:
            case SMASH:
            case PRESS:
                return player == side ? 1 : -1;
            case BLOCK:
                return player == side ? -1 : 1;
            case CLEAR:
            case FREEZE:
            case BASE:
            default:
                return 1;
        }
    }
}

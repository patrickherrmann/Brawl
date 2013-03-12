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

    public static List<Move> getAllMoves() {
        List<Move> moves = new ArrayList<Move>();

        for (Player player : Player.values()) {
            for (Player side : Player.values()) {
                for (BasePosition basePosition : BasePosition.values()) {
                    moves.add(new Move(player, side, basePosition));
                }
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
        return 1;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pwherrma
 */
public abstract class Move {

    public static List<Move> getAllMoves(Player player) {
        List<Move> allMoves = new ArrayList<Move>();

        for (BasePosition basePosition : BasePosition.values()) {
            for (Player side : Player.values()) {
                allMoves.add(new PlayCardAction(player, side, basePosition));
            }
        }

        allMoves.add(new DrawAction(player));

        return allMoves;
    }

    protected Player player;

    protected Move(Player player) {
        this.player = player;
    }

    public abstract void perform(GameState gameState);

    public abstract int getHeuristic(GameState gameState);

    public abstract MoveAnalysis analyze(GameState gameState);
}

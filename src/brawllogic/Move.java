/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

/**
 *
 * @author pwherrma
 */
public abstract class Move {

    protected Player player;

    protected Move(Player player) {
        this.player = player;
    }

    public abstract void perform(GameState gameState);

    public abstract int getHeuristic(GameState gameState);

    public abstract MoveAnalysis analyze(GameState gameState);
}

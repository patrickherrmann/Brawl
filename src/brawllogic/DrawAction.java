/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

/**
 *
 * @author pwherrma
 */
public class DrawAction extends Move {

    protected DrawAction(Player player) {
        super(player);
    }

    @Override
    public void perform(GameState gameState) {
        gameState.draw(player);
    }

    @Override
    public int getHeuristic(GameState gameState) {
        return 1;
    }

    @Override
    public MoveAnalysis analyze(GameState gameState) {
        return gameState.canDraw(player);
    }

}

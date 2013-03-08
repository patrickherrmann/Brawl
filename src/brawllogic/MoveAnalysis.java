/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

/**
 *
 * @author patrick
 */
public class MoveAnalysis extends GameplayAnalysis {

    private Player player;

    public MoveAnalysis(Player player, boolean legal, String message) {
        super(legal, message);
        this.player = player;
    }

    public MoveAnalysis(Player player, GameplayAnalysis gameplayAnalysis) {
        super(gameplayAnalysis);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}

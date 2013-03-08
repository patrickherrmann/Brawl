/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

/**
 *
 * @author patrick
 */
public class Move {

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
        return 0;
    }
}

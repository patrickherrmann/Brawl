/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

/**
 *
 * @author patrick
 */
public class TournamentModeGameState extends GameState {

    public TournamentModeGameState(Fighter left, Fighter right) {
        super(left, right);
    }

    @Override
    public boolean canPlay(Player player) {
        return true;
    }
}

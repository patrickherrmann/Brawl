/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

import java.util.Random;

/**
 *
 * @author patrick
 */
public class TrainingModeGameState extends GameState {

    private Player current;
    private boolean hasDrawn;

    public TrainingModeGameState(Fighter left, Fighter right) {
        super(left, right);

        current = new Random().nextBoolean() ? Player.LEFT : Player.RIGHT;
        hasDrawn = false;
        draw(current);
    }

    @Override
    public boolean canPlay(Player player) {
        return player == current;
    }

    @Override
    public void move(Move move) {
        super.move(move);
        current = current.getOpponent();
        setChanged();
        notifyObservers();
    }

    @Override
    public final void draw(Player player) {

        if (hasDrawn) {
            current = current.getOpponent();
            setChanged();
            notifyObservers();
        } else {
            super.draw(player);
        }
        hasDrawn = ! hasDrawn;
    }
}

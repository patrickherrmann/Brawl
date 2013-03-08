/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

/**
 *
 * @author patrick
 */
public class Controller {

    private GameState gameState;
    private KeyMap keyMap;

    public Controller(GameState gameState, KeyMap keyMap) {
        this.gameState = gameState;
        this.keyMap = keyMap;
    }

    public MoveAnalysis handleInput(char key) {

        MoveAnalysis analysis;

        // Check if they tried to draw
        for (Player player : Player.values()) {
            if (key == keyMap.getDrawKey(player)) {
                if (gameState.canPlay(player)) {
                    analysis = gameState.canDraw(player);
                    if (analysis.isLegal()) {
                        gameState.draw(player);
                    }
                    return analysis;
                }
            }
        }

        // Handle every other kind of move
        Move move = keyMap.getMoves().get(key);

        if (move == null) {
            return null; // The key is meaningless
        }

        analysis = gameState.isLegal(move);

        if (analysis.isLegal()) {
            gameState.move(move);
        }

        return analysis;
    }

    public GameState getGameState() {
        return gameState;
    }

    public KeyMap getKeyMap() {
        return keyMap;
    }
}

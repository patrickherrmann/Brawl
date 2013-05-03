package brawllogic;

import java.util.Map;

/**
 *
 * @author patrick
 */
public class HumanPlayer {

    private Map<Character, Move> keyMap;

    public HumanPlayer(Map<Character, Move> keyMap) {
        this.keyMap = keyMap;
    }

    public MoveAnalysis handleInput(char key, GameState gameState) {

        synchronized (gameState) {

            Move move = keyMap.get(key);

            if (move == null) {
                return null; // The key is not mapped to any action
            }

            MoveAnalysis analysis = move.analyze(gameState);

            if (analysis.isLegal()) {
                move.perform(gameState);
            }

            return analysis;
        }
    }
}

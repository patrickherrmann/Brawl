package brawllogic;

/**
 *
 * @author patrick
 */
public class HumanPlayer {

    private KeyMap keyMap;

    public HumanPlayer(KeyMap keyMap) {
        this.keyMap = keyMap;
    }

    public MoveAnalysis handleInput(char key, GameState gameState) {

        synchronized (gameState) {

            Move move = keyMap.getMoves().get(key);

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

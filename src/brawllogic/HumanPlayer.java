package brawllogic;

/**
 *
 * @author patrick
 */
public class HumanPlayer {

    private Player player;
    private KeyMap keyMap;

    public HumanPlayer(Player player, KeyMap keyMap) {
        this.player = player;
        this.keyMap = keyMap;
    }

    public MoveAnalysis handleInput(char key, GameState gameState) {

        synchronized (gameState) {

            MoveAnalysis analysis;

            if (key == keyMap.getDrawKey()) {
                if (gameState.canPlay(player)) {
                    analysis = gameState.canDraw(player);
                    if (analysis.isLegal()) {
                        gameState.draw(player);
                    }
                    return analysis;
                }
            }

            // Handle every other kind of move
            Move move = keyMap.getMoves().get(key);

            if (move == null) {
                return null; // The key is meaningless
            }

            analysis = gameState.analyzeMove(move);

            if (analysis.isLegal()) {
                gameState.move(move);
            }
            return analysis;
        }
    }
}

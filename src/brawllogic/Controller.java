package brawllogic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patrick
 */
public final class Controller {

    private GameState gameState;
    private HumanPlayer[] humanPlayers;

    public Controller(GameState gameState, HumanPlayer... humanPlayers) {
        this.gameState = gameState;
        this.humanPlayers = humanPlayers;
    }

    public List<MoveAnalysis> handleInput(char key) {

        List<MoveAnalysis> analyses = new ArrayList<MoveAnalysis>();

        for (HumanPlayer humanPlayer : humanPlayers) {
            analyses.add(humanPlayer.handleInput(key, gameState));
        }

        return analyses;
    }

    public GameState getGameState() {
        return gameState;
    }
}

package brawllogic;

import deepdish.LoopThread;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patrick
 */
public class ComputerPlayer extends LoopThread {

    private final GameState gameState;
    private List<Move> moves;

    public ComputerPlayer(Player player, GameState gameState, double mps) {
        super("Computer Player", mps);
        
        this.gameState = gameState;
        this.moves = new ArrayList<Move>();

        moves = Move.getAllMoves(player);
    }

    @Override
    public void performTask() {

        int bestHeuristic = 0;
        Move bestMove = null;

        synchronized (gameState) {

            for (Move move : moves) {
                int heuristic = move.getHeuristic(gameState);
                if (heuristic > bestHeuristic) {
                    MoveAnalysis analysis = move.analyze(gameState);
                    if (analysis.isLegal()) {
                        bestHeuristic = heuristic;
                        bestMove = move;
                    }
                }
            }

            if (bestMove != null) {
                bestMove.perform(gameState);
            }

            if (gameState.isGameOver()) {
                this.terminate();
            }
        }
    }
}

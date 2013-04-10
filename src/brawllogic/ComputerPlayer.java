package brawllogic;

import deepdish.LoopThread;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patrick
 */
public class ComputerPlayer extends LoopThread {

    private Player player;
    private final GameState gameState;
    private List<Move> moves;

    public ComputerPlayer(Player player, GameState gameState, int mps) {
        super("Computer Player", mps);

        this.player = player;
        this.gameState = gameState;
        this.moves = new ArrayList<Move>();

        for (BasePosition basePosition : BasePosition.values()) {
            for (Player side : Player.values()) {
                moves.add(new PlayCardAction(player, side, basePosition));
            }
        }

        moves.add(new DrawAction(player));
    }

    @Override
    public void performTask() {

        if (gameState.isGameOver()) {
            this.terminate();
        }

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
        }
    }
}

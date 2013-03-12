package brawllogic;

import java.util.List;

/**
 *
 * @author patrick
 */
public class AI extends Thread {

    private Player player;
    private final GameState gameState;
    private int sleepTime;

    private List<Move> moves = Move.getAllMoves();

    public AI(Player player, GameState gameState, double difficulty) {
        this.player = player;
        this.gameState = gameState;
        this.sleepTime = (int)((1.0 - difficulty) * 2800 + 200);
    }

    @Override
    public void run() {

        while (!gameState.isGameOver()) {

            int bestHeuristic = 0;
            Move bestMove = null;

            synchronized (gameState) {

                for (Move move : moves) {
                    int heuristic = move.getHeuristic(gameState);
                    if (heuristic > bestHeuristic) {
                        MoveAnalysis analysis = gameState.analyzeMove(move);
                        if (analysis.isLegal()) {
                            bestHeuristic = heuristic;
                            bestMove = move;
                        }
                    }
                }

                if (bestMove == null) {
                    if (gameState.canDraw(player).isLegal()) {
                        gameState.draw(player);
                    }
                } else {
                    gameState.move(bestMove);
                }
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
            }
        }
    }
}

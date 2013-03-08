/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

/**
 *
 * @author patrick
 */
public class GameplayAnalysis {

    private boolean legal;
    private String message;

    public GameplayAnalysis(boolean legal, String message) {
        this.legal = legal;
        this.message = message;
    }

    public GameplayAnalysis(GameplayAnalysis gameplayAnalysis) {
        this.legal = gameplayAnalysis.legal;
        this.message = gameplayAnalysis.message;
    }

    public boolean isLegal() {
        return legal;
    }

    public String getMessage() {
        return message;
    }
}

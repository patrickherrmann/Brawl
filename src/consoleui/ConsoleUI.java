/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package consoleui;

import brawllogic.*;
import java.util.*;

/**
 *
 * @author patrick
 */
public class ConsoleUI implements Observer {
    
    private Controller controller;
    
    public ConsoleUI(Controller controller) {
        this.controller = controller;
        controller.getGameState().addObserver(this);
        printGameState();
    }
    
    public void start() {
        Scanner in = new Scanner(System.in);
        char key;
        
        while (!controller.getGameState().isGameOver()) {
            
            key = in.nextLine().charAt(0);

            List<MoveAnalysis> analyses = controller.handleInput(key);

            for (MoveAnalysis analysis : analyses) {
                if (analysis != null && !analysis.isLegal()) {
                    System.out.println(analysis.getMessage());
                }
            }
        }
        
        Player winner = controller.getGameState().getWinner();
        
        if (winner == null) {
            System.out.println("Tie game!");
        } else {
            System.out.println((winner == Player.LEFT ? "Left" : "Right") + " player wins!");
        }
    }
    
    private void printGameState() {
        
        System.out.println();
        
        for (Player player : Player.values()) {
            
            List<Card> discard = controller.getGameState().getDiscard(player);
            
            String active = discard.isEmpty() ? "<empty>" : discard.get(discard.size() - 1).getShorthand();
            
            System.out.println(controller.getGameState().getFighter(player).getName() + ": " + active);
        }
        
        System.out.println();
        
        for (Base base : controller.getGameState().getBases()) {

            List<Card> leftStack = base.getBaseStack(Player.LEFT).getStack();

            for (int i = leftStack.size() - 1; i >= 0; i--) {
                System.out.print(" " + leftStack.get(i).getShorthand());
            }
            
            System.out.print(" |" + base.getBaseCards().get(base.getBaseCards().size() - 1).getShorthand() + "|");
            
            for (Card card : base.getBaseStack(Player.RIGHT).getStack()) {
                System.out.print(" " + card.getShorthand());
            }
            System.out.println();
        }
        System.out.println();
    }
    
    

    @Override
    public void update(Observable o, Object o1) {
        printGameState();
    }
}

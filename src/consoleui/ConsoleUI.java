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
    
    private BrawlGame game;
    
    public ConsoleUI(BrawlGame game) {
        this.game = game;
        game.getState().addObserver(this);
        printGameState();
    }
    
    public void start() {
        Scanner in = new Scanner(System.in);
        
        while (!game.getState().isGameOver()) {
            
            try {
                handleInput(game, in.nextLine());
            } catch (IllegalMoveException ex) {
                System.out.println();
                System.out.print(game.getState().getFighter(ex.getPlayer()).getName() + ": ");
                System.out.println(ex.getMessage());
            }
        }
        
        Player winner = game.getState().getWinner();
        
        if (winner == null) {
            System.out.println("Tie game!");
        } else {
            System.out.println((winner == Player.LEFT ? "Left" : "Right") + " player wins!");
        }
    }
    
    private void printGameState() {
        
        System.out.println();
        
        for (Player player : Player.values()) {
            
            Stack<Card> discard = game.getState().getDiscard(player);
            
            String active = discard.isEmpty() ? "<empty>" : discard.peek().getShorthand();
            
            System.out.println(game.getState().getFighter(player).getName() + ": " + active);
        }
        
        System.out.println();
        
        for (Base base : game.getState().getBases()) {

            Stack<Card> leftStack = base.getBaseStack(Player.LEFT).getStack();

            for (int i = leftStack.size() - 1; i >= 0; i--) {
                System.out.print(" " + leftStack.get(i).getShorthand());
            }
            
            System.out.print(" |" + base.getBaseCards().peek().getShorthand() + "|");
            
            for (Card card : base.getBaseStack(Player.RIGHT).getStack()) {
                System.out.print(" " + card.getShorthand());
            }
            System.out.println();
        }
        System.out.println();
        
        Player current = game.getCurrentPlayer();
        
        if (current != null) {
            System.out.println(game.getState().getFighter(current).getName() + " to play:");
            System.out.println();
        }
    }
    
    private static void handleInput(BrawlGame game, String input) throws IllegalMoveException {
        
        BasePosition bp;
        Player s;
        Player p;
        
        switch (input.charAt(0)) {
            // LEFT PLAYER CONTROLS
            case 'w':
                bp = BasePosition.HIGH;
                s = Player.LEFT;
                p = Player.LEFT;
                break;
            case 'e':
                bp = BasePosition.HIGH;
                s = Player.RIGHT;
                p = Player.LEFT;
                break;
            case 's':
                bp = BasePosition.MID;
                s = Player.LEFT;
                p = Player.LEFT;
                break;
            case 'd':
                bp = BasePosition.MID;
                s = Player.RIGHT;
                p = Player.LEFT;
                break;
            case 'x':
                bp = BasePosition.LOW;
                s = Player.LEFT;
                p = Player.LEFT;
                break;
            case 'c':
                bp = BasePosition.LOW;
                s = Player.RIGHT;
                p = Player.LEFT;
                break;
            case 'z':
                game.draw(Player.LEFT);
                return;
            // RIGHT PLAYER CONTROLS
            case 'o':
                bp = BasePosition.HIGH;
                s = Player.LEFT;
                p = Player.RIGHT;
                break;
            case 'p':
                bp = BasePosition.HIGH;
                s = Player.RIGHT;
                p = Player.RIGHT;
                break;
            case 'l':
                bp = BasePosition.MID;
                s = Player.LEFT;
                p = Player.RIGHT;
                break;
            case ';':
                bp = BasePosition.MID;
                s = Player.RIGHT;
                p = Player.RIGHT;
                break;
            case '.':
                bp = BasePosition.LOW;
                s = Player.LEFT;
                p = Player.RIGHT;
                break;
            case '/':
                bp = BasePosition.LOW;
                s = Player.RIGHT;
                p = Player.RIGHT;
                break;
            case ',':
                game.draw(Player.RIGHT);
                return;
            default:
                System.out.println("Unrecognized command.");
                return;
        }
        
        game.tryMove(p, bp, s);
    }

    @Override
    public void update(Observable o, Object o1) {
        printGameState();
    }
}

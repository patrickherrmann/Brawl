package brawllogic;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author Patrick Herrmann
 */
public final class BaseStack {
    
    private CardColor color = CardColor.NONE;
    private Stack<Card> stack = new Stack<Card>();
    
    public GameplayAnalysis canPlay(Card card)  {
        
        if (card.getType().getCategory() != CardCategory.STACKER)
            return new GameplayAnalysis(false, "This type of card cannot be stacked on either side of a base.");
        
        if (color == CardColor.NONE) {
            
            if (card.getType() != CardType.HIT)
                return new GameplayAnalysis(false, "The first card played on an empty base must be a hit card.");
            
            return new GameplayAnalysis(true, "This is a legal first move on the side of a base.");
        } else {
            
            if (card.getType() == CardType.PRESS) {
                
                if (stack.peek().getType() != CardType.BLOCK)
                    return new GameplayAnalysis(false, "A press must be played on a block.");
                
                return new GameplayAnalysis(true, "You may play a press on this block.");
            } else {
                
                if (card.getColor() != color)
                    return new GameplayAnalysis(false, "This color card cannot be stacked in this pile.");
                
                if (stack.peek().getType() == CardType.BLOCK)
                    return new GameplayAnalysis(false, "This card cannot be played on a blocked pile.");
                
                if (stack.peek().getType() == CardType.PRESS) {
                    
                    if (card.getType() != CardType.HIT)
                        return new GameplayAnalysis(false, "Only hits can be played on presses.");
                    
                }
                
                return new GameplayAnalysis(true, "You may stack this card here.");
            }
        }
    }

    public void play(Card card) {

        if (color == CardColor.NONE) {
            color = card.getColor();
        }

        stack.push(card);
    }
    
    public int getScore() {
        
        int score = 0;
        
        for (Card card : stack) {
            if (card.getType() == CardType.HIT)
                score++;
            else if (card.getType() == CardType.SMASH)
                score += 2;
        }
        
        return score;
    }
    
    public List<Card> getStack() {
        return Collections.unmodifiableList(stack);
    }
}

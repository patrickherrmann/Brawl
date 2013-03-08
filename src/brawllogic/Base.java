package brawllogic;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author Patrick Herrmann
 */
public final class Base {
    
    private Map<Player, BaseStack> baseStacks = new EnumMap(Player.class);
    private Stack<Card> baseCards = new Stack<Card>();
    
    public Base(Card baseCard) {
        
        if (baseCard.getType().getCategory() != CardCategory.BASE)
            throw new IllegalArgumentException("Base must be instantiated with a base card.");
        
        for (Player player : Player.values())
            baseStacks.put(player, new BaseStack());
        
        baseCards.push(baseCard);
    }
    
    public GameplayAnalysis canPlay(Player side, Card card) {
        
        if (side == null)
            throw new IllegalArgumentException("side cannot be null.");
        
        if (isFrozen())
            return new GameplayAnalysis(false, "Cannot play on a frozen base.");
        
        if (card.getType() == CardType.FREEZE)
            return new GameplayAnalysis(true, "A freeze can be played on this base.");
        else
            return baseStacks.get(side).canPlay(card);
    }

    public void play(Player side, Card card) {
        if (card.getType() == CardType.FREEZE)
            baseCards.push(card);
        else
            baseStacks.get(side).play(card);
    }
    
    public Player getWinner() {
        
        int leftScore = baseStacks.get(Player.LEFT).getScore();
        int rightScore = baseStacks.get(Player.RIGHT).getScore();
        
        if (leftScore == rightScore)
            return baseCards.firstElement().getOwner();
        
        return leftScore > rightScore ? Player.LEFT : Player.RIGHT;
    }
    
    public boolean isFrozen() {
        return baseCards.peek().getType() == CardType.FREEZE;
    }
    
    public List<Card> getBaseCards() {
        return Collections.unmodifiableList(baseCards);
    }
    
    public BaseStack getBaseStack(Player side) {
        return baseStacks.get(side);
    }
}

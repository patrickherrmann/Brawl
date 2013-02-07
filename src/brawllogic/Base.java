package brawllogic;

import java.util.EnumMap;
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
    
    public void tryMove(Player side, Card card) throws GameplayException {
        
        if (side == null)
            throw new IllegalArgumentException("side cannot be null.");
        
        if (isFrozen())
            throw new GameplayException("Cannot play on a frozen base.");
        
        if (card.getType() == CardType.FREEZE)
            baseCards.push(card);
        else
            baseStacks.get(side).tryMove(card);
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
    
    public Stack<Card> getBaseCards() {
        return baseCards;
    }
    
    public BaseStack getBaseStack(Player side) {
        return baseStacks.get(side);
    }
}

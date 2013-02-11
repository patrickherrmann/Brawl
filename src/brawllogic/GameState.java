package brawllogic;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author Patrick Herrmann
 */
public final class GameState {
    
    private List<Base> bases = new LinkedList<Base>();
    private Map<Player, Fighter> fighters;
    private Map<Player, Stack<Card>> decks = new EnumMap(Player.class);
    private Map<Player, Stack<Card>> discards = new EnumMap(Player.class);
    private List<Card> cardsOutOfPlay = new ArrayList<Card>();
    
    public GameState(Map<Player, Fighter> fighters) {
        
        if (fighters == null)
            throw new IllegalArgumentException("fighters cannot be null.");
        
        this.fighters = fighters;
        
        for (Player player : Player.values()) {
            decks.put(player, fighters.get(player).loadDeck(player));
            Card topCardBase = decks.get(player).pop();
            topCardBase.flip();
            bases.add(new Base(topCardBase));
            discards.put(player, new Stack());
        }
    }
    
    void draw(Player player) throws IllegalMoveException {
        
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null");
        
        Stack<Card> deck = decks.get(player);
        
        if (deck.isEmpty())
            throw new IllegalMoveException("No cards left to draw.", player);
        
        Card topCard = deck.pop();
        
        topCard.flip();
        
        discards.get(player).push(topCard);
    }
    
    private int getBaseIndex(BasePosition basePosition) throws GameplayException {
        
        if (bases.size() == 1) {
            
            if (basePosition != BasePosition.MID)
                throw new GameplayException("There is no base on this row.");
            
            return 0;
            
        } else if (bases.size() == 2) {
            
            if (basePosition == BasePosition.LOW)
                throw new GameplayException("There is no base on this row.");
            
        }
        
        return basePosition.getIndex();
    }
    
    private void tryClear(BasePosition basePosition, Card card) throws GameplayException {
        
        if (bases.size() == 1)
            throw new GameplayException("Cannot clear only base.");
        
        int baseIndex = getBaseIndex(basePosition);
        
        if (bases.size() == 3 && baseIndex == 1)
            throw new GameplayException("Cannot clear middle base.");
        
        Base base = bases.get(baseIndex);
        
        if (base.isFrozen())
            throw new GameplayException("Cannot clear a frozen base.");
        
        base.getBaseCards().push(card);
        
        cardsOutOfPlay.addAll(base.getBaseCards());
        
        for (Player player : Player.values())
            cardsOutOfPlay.addAll(base.getBaseStack(player).getStack());
        
        bases.remove(baseIndex);
    }
    
    void tryPlayBase(BasePosition basePosition, Card card) throws GameplayException {
        
        if (bases.size() == 3)
            throw new GameplayException("A maximum of three bases can be in play at once.");
        
        if (basePosition == BasePosition.MID)
            throw new GameplayException("Bases can only be added above or below existing bases.");
        
        
        Base base = new Base(card);
        
        if (basePosition == BasePosition.HIGH) {
            bases.add(0, base);
        } else {
            bases.add(base);
        }
    }
    
    void tryPlayCard(BasePosition basePosition, Card card, Player side) throws GameplayException {
        
        if (card.getType() == CardType.BASE) {
            tryPlayBase(basePosition, card);
        } else if (card.getType() == CardType.CLEAR) {
            tryClear(basePosition, card);
        } else {
            Base base = bases.get(getBaseIndex(basePosition));
            base.tryMove(side, card);
        }
    }
    
    void tryMove(Player player, BasePosition basePosition, Player side) throws IllegalMoveException {
        
        Stack<Card> discard = discards.get(player);
        
        if (discard.isEmpty())
            throw new IllegalMoveException("You must draw a card before you can play.", player);
        
        Card card = discard.pop();
        
        try {
            tryPlayCard(basePosition, card, side);
        } catch (GameplayException e) {
            discard.push(card);
            throw new IllegalMoveException(e.getMessage(), player);
        }
    }
    
    public boolean isGameOver() {
        
        for (Base base : bases) {
            
            if (!base.isFrozen())
                return false;
            
        }
        
        return true;
    }
    
    public Player getWinner() {
        
        int leftScore = 0;
        int rightScore = 0;
        
        for (Base base : bases) {
            if (base.getWinner() == Player.LEFT) {
                leftScore++;
            } else {
                rightScore++;
            }
        }
        
        if (rightScore == leftScore)
            return null;
        
        return leftScore > rightScore ? Player.LEFT : Player.RIGHT;
    }
    
    public Stack<Card> getDeck(Player player) {
        return decks.get(player);
    }
    
    public List<Card> getCardsOutOfPlay() {
        return cardsOutOfPlay;
    }
    
    public Stack<Card> getDiscard(Player player) {
        return discards.get(player);
    }
    
    public Fighter getFighter(Player player) {
        return fighters.get(player);
    }
    
    public List<Base> getBases() {
        return bases;
    }
    
    public List<Card> getAllCardsInPlay() { // This includes cards yet to be drawn
        
        List<Card> allCards = new ArrayList<Card>();
        
        for (Stack<Card> deck : decks.values())
            for (Card card : deck)
                allCards.add(card);
        
        for (Stack<Card> discard : discards.values())
            for (Card card : discard)
                allCards.add(card);
        
        for (Base base : bases) {
            
            for (Card card : base.getBaseCards())
                allCards.add(card);
            
            for (Player player : Player.values())
                for (Card card : base.getBaseStack(player).getStack())
                    allCards.add(card);
        }
        
        return allCards;
    }
}

package brawllogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Stack;

/**
 * @author Patrick Herrmann
 */
public abstract class GameState extends Observable {
    
    private List<Base> bases = new LinkedList<Base>();
    private Map<Player, Fighter> fighters;
    private Map<Player, Stack<Card>> decks = new EnumMap(Player.class);
    private Map<Player, Stack<Card>> discards = new EnumMap(Player.class);
    private List<Card> cardsOutOfPlay = new ArrayList<Card>();
    
    protected GameState(Fighter left, Fighter right) {
        
        fighters = new EnumMap<Player, Fighter>(Player.class);
        fighters.put(Player.LEFT, left);
        fighters.put(Player.RIGHT, right);
        
        for (Player player : Player.values()) {
            decks.put(player, fighters.get(player).loadDeck(player));
            Card topCardBase = decks.get(player).pop();
            topCardBase.flip();
            bases.add(new Base(topCardBase));
            discards.put(player, new Stack());
        }
    }

    public abstract boolean canPlay(Player player);
    
    public MoveAnalysis canDraw(Player player) {
        
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null");
        
        Stack<Card> deck = decks.get(player);
        
        if (deck.isEmpty())
            return new MoveAnalysis(player, false, "No cards left to draw.");
        
        return new MoveAnalysis(player, true, "The player can draw.");
    }
    
    public void draw(Player player) {
        Card topCard = decks.get(player).pop();
        
        topCard.flip();
        
        discards.get(player).push(topCard);
        
        setChanged();
        notifyObservers();
    }
    
    private int getBaseIndex(BasePosition basePosition) {
        
        if (bases.size() == 1) {
            
            if (basePosition != BasePosition.MID)
                return -1;
            
            return 0;
            
        } else if (bases.size() == 2) {
            
            if (basePosition == BasePosition.LOW)
                return -1;
            
        }
        
        return basePosition.getIndex();
    }
    
    private GameplayAnalysis canClear(BasePosition basePosition) {
        
        if (bases.size() == 1)
            return new GameplayAnalysis(false, "Cannot clear only base.");
        
        int baseIndex = getBaseIndex(basePosition);

        if (baseIndex == -1)
            return new GameplayAnalysis(false, "There is no base at the specified position");
        
        if (bases.size() == 3 && baseIndex == 1)
            return new GameplayAnalysis(false, "Cannot clear middle base.");
        
        Base base = bases.get(baseIndex);
        
        if (base.isFrozen())
            return new GameplayAnalysis(false, "Cannot clear a frozen base.");
        
        return new GameplayAnalysis(true, "The base in this position can be cleared");
    }

    private void clear(BasePosition basePosition, Card card) {

        int baseIndex = getBaseIndex(basePosition);

        Base base = bases.get(baseIndex);

        cardsOutOfPlay.add(card);
        cardsOutOfPlay.addAll(base.getBaseCards());

        for (Player player : Player.values())
            cardsOutOfPlay.addAll(base.getBaseStack(player).getStack());

        bases.remove(baseIndex);
    }
    
    private GameplayAnalysis canPlayBase(BasePosition basePosition, Card card) {
        
        if (bases.size() == 3)
            return new GameplayAnalysis(false, "A maximum of three bases can be in play at once.");
        
        if (basePosition == BasePosition.MID)
            return new GameplayAnalysis(false, "Bases can only be added above or below existing bases.");
        
        return new GameplayAnalysis(true, "A base can be played in this position.");
    }

    private void playBase(BasePosition basePosition, Card card) {
        Base base = new Base(card);

        if (basePosition == BasePosition.HIGH) {
            bases.add(0, base);
        } else {
            bases.add(base);
        }
    }
    
    private GameplayAnalysis canPlayCard(BasePosition basePosition, Card card, Player side) {
        
        if (card.getType() == CardType.BASE) {
            return canPlayBase(basePosition, card);
        } else if (card.getType() == CardType.CLEAR) {
            return canClear(basePosition);
        } else {
            int baseIndex = getBaseIndex(basePosition);

            if (baseIndex == -1)
                return new GameplayAnalysis(false, "There is no base at the specified position");

            Base base = bases.get(baseIndex);
            return base.canPlay(side, card);
        }
    }

    private void playCard(BasePosition basePosition, Card card, Player side) {
        
        if (card.getType() == CardType.BASE) {
            playBase(basePosition, card);
        } else if (card.getType() == CardType.CLEAR) {
            clear(basePosition, card);
        } else {
            Base base = bases.get(getBaseIndex(basePosition));
            base.play(side, card);
        }
    }
    
    public MoveAnalysis analyzeMove(Move move)  {

        Player player = move.getPlayer();
        Stack<Card> discard = discards.get(player);
        
        if (discard.isEmpty())
            return new MoveAnalysis(player, false, "You must draw a card before you can play.");
        
        return new MoveAnalysis(player, canPlayCard(move.getBasePosition(), discard.peek(), move.getSide()));
    }

    public void move(Move move) {
        playCard(move.getBasePosition(), discards.get(move.getPlayer()).pop(), move.getSide());
        setChanged();
        notifyObservers();
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
    
    public List<Card> getDeck(Player player) {
        return Collections.unmodifiableList(decks.get(player));
    }
    
    public List<Card> getCardsOutOfPlay() {
        return Collections.unmodifiableList(cardsOutOfPlay);
    }
    
    public List<Card> getDiscard(Player player) {
        return Collections.unmodifiableList(discards.get(player));
    }
    
    public Fighter getFighter(Player player) {
        return fighters.get(player);
    }
    
    public List<Base> getBases() {
        return Collections.unmodifiableList(bases);
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

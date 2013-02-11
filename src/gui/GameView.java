package gui;

import brawllogic.Base;
import brawllogic.BaseStack;
import brawllogic.Card;
import brawllogic.GameState;
import brawllogic.Player;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author Patrick Herrmann
 */
public class GameView {
    
    public static final int DECK_OFFSET = 500;
    public static final int CARD_WIDTH = 100;
    public static final int CARD_HEIGHT = 200;
    public static final int PADDING = 30;
    
    public static final int BASE_PADDING = 300;
    
    private GameState gameState;
    private Map<Card, CardView> cardViews;
    private static int zindex = Integer.MIN_VALUE;
    
    public GameView(GameState gameState) {
        this.gameState = gameState;
        
        cardViews = new HashMap<Card, CardView>();
        
        for (Card card : gameState.getAllCardsInPlay())
            cardViews.put(card, new CardView(card, gameState.getFighter(card.getOwner())));
    }
    
    public void update() { // Use mathematical coordinates, with origin at CENTER of the screen
        
        Stack<Card> pile;
        CardView cv;
        
        // Position decks
        
        pile = gameState.getDeck(Player.LEFT);
        
        if (!pile.isEmpty()) {
            cv = cardViews.get(pile.peek());
            cv.setPosition(-DECK_OFFSET, -(CARD_HEIGHT - PADDING));
            cv.setZIndex(zindex++);
            cv.setVisibility(true);
        }
        
        pile = gameState.getDeck(Player.RIGHT);
        
        if (!pile.isEmpty()) {
            cv = cardViews.get(pile.peek());
            cv.setPosition(DECK_OFFSET, -(CARD_HEIGHT - PADDING));
            cv.setZIndex(zindex++);
            cv.setVisibility(true);
        }
        
        // Position active card and discard piles
        
        pile = gameState.getDiscard(Player.LEFT);
        
        if (!pile.isEmpty()) {
            
            for (int i = 0; i < pile.size() - 1; i++) {
                cv = cardViews.get(pile.get(i));
                cv.setPosition(-DECK_OFFSET, CARD_HEIGHT - PADDING);
                cv.setZIndex(zindex++);
            }
            
            cv = cardViews.get(pile.peek());
            cv.setPosition(-(DECK_OFFSET - PADDING), 0);
            cv.setZIndex(zindex++);
        }
        
        pile = gameState.getDiscard(Player.RIGHT);
        
        if (!pile.isEmpty()) {
            
            for (int i = 0; i < pile.size() - 1; i++) {
                cv = cardViews.get(pile.get(i));
                cv.setPosition(DECK_OFFSET, CARD_HEIGHT - PADDING);
                cv.setZIndex(zindex++);
            }
            
            cv = cardViews.get(pile.peek());
            cv.setPosition(DECK_OFFSET - PADDING, 0);
            cv.setZIndex(zindex++);
        }
        
        // BASES
        
        List<Base> bases = gameState.getBases();
        
        double baseStart = (bases.size() - 1) * BASE_PADDING / 2;
        double baseY;
        
        for (int i = 0; i < bases.size(); i++) {
            Base base = bases.get(i);
            baseY = baseStart - i * BASE_PADDING;
            
            for (Player side : Player.values()) {
                Stack<Card> baseStack = base.getBaseStack(side).getStack();
                double offset = side == Player.LEFT ? -50 : 50;
                
                for (int j = 0; j < baseStack.size(); j++) {
                    cv = cardViews.get(baseStack.get(j));
                    cv.setPosition(offset * (1.5 + j), baseY);
                    cv.setZIndex(zindex++);
                }
            }
            
            pile = base.getBaseCards();
            
            cv = cardViews.get(pile.firstElement());
            cv.setPosition(0, baseY);
            cv.setRotation(Math.PI / 2 * (pile.firstElement().getOwner() == Player.LEFT ? -1 : 1));
            cv.setZIndex(zindex++);
            cv.setVisibility(true);
            
            for (int j = 1; j < pile.size(); j++) {
                cv = cardViews.get(pile.get(j));
                cv.setPosition(0, baseY);
                cv.setRotation(Math.PI * -0.25);
                cv.setZIndex(zindex++);
            }
        }
        
        // Out of play
        
        for (Card card : gameState.getCardsOutOfPlay()) {
            cv = cardViews.get(card);
            cv.setVisibility(false);
        }
    }
    
    public Collection<CardView> getCardViews() {
        return cardViews.values();
    }
}

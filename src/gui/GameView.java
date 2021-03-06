package gui;

import brawllogic.Base;
import brawllogic.Card;
import brawllogic.GameState;
import brawllogic.Player;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Patrick Herrmann
 */
public final class GameView implements Observer {
    
    private static final int DECK_OFFSET = 400;
    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 162;
    private static final int PADDING = 30;
    
    private static final int BASE_PADDING = 250;
    
    private  GameState gameState;
    private Map<Card, CardView> cardViews;
    private int z;
    
    public GameView(GameState gameState) {
        this.gameState = gameState;
        gameState.addObserver(this);
        
        cardViews = new HashMap<Card, CardView>();
        
        for (Card card : gameState.getAllCardsInPlay())
            cardViews.put(card, new CardView(card, gameState.getFighter(card.getOwner())));
        
        updateView();
    }
    
    private void updateView() {

        z = 0;
        int m, i;
        CardView cv;

        // Draw each player's decks
        for (Player player : Player.values()) {
            m = player == Player.LEFT ? -1 : 1;

            List<Card> deck = gameState.getDeck(player);

            for (Card card : deck) {
                cv = cardViews.get(card);
                cv.animateLocation(m * DECK_OFFSET, -BASE_PADDING);
                cv.setZIndex(z++);
            }

            List<Card> discard = gameState.getDiscard(player);

            double x = m * DECK_OFFSET;
            double y = BASE_PADDING;

            for (i = 0; i < discard.size() - 1; i++) {
                cv = cardViews.get(discard.get(i));
                cv.animateLocation(x, y);
                cv.setZIndex(z++);
            }

            if (gameState.canPlay(player)) {
                x = m * (DECK_OFFSET - (CARD_WIDTH - PADDING));
                y = 0;
            }

            if (!discard.isEmpty()) {
                cv = cardViews.get(discard.get(discard.size() - 1));
                cv.animateLocation(x, y);
            }
        }

        double baseY = (gameState.getBases().size() - 1) * BASE_PADDING / 2;

        // Draw each base
        for (Base base : gameState.getBases()) {

            for (Card card : base.getBaseCards()) {
                m = card.getOwner() == Player.LEFT ? -1 : 1;
                cv = cardViews.get(card);
                cv.animateLocation(0, baseY);
                cv.setZIndex(z++);
                cv.animateRotation(m * Math.PI / 2);
            }

            
            for (Player side : Player.values()) {
                m = side == Player.LEFT ? -1 : 1;

                List<Card> stack = base.getBaseStack(side).getStack();

                for (i = 0; i < stack.size(); i++) {
                    cv = cardViews.get(base.getBaseStack(side).getStack().get(i));
                    int n = i;
                    if (stack.size() > 4) n -= (stack.size() - 4);
                    if (n < 0) n = 0;
                    cv.animateLocation(m * (4 + n) * PADDING, baseY);
                    cv.setZIndex(z++);
                }
            }

            baseY -= BASE_PADDING;
        }

        for (Card card : gameState.getCardsOutOfPlay()) {
            cv = cardViews.get(card);
            cv.animateLocation(0, 1000);
            cv.animateRotation(50);
            cv.setZIndex(z++);
        }
    }
    
    public Collection<CardView> getCardViews() {
        return cardViews.values();
    }

    @Override
    public void update(Observable o, Object o1) {
        updateView();
    }
}

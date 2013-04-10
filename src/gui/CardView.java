package gui;

import brawllogic.Card;
import brawllogic.CardType;
import brawllogic.Fighter;
import deepdish.MovableObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 * @author Patrick Herrmann
 */
public class CardView extends MovableObject {
    
    private static final double SKEW = 0.02;

    private Card card;
    private Fighter fighter;
    private Random rng;

    public CardView(Card card, Fighter fighter) {
        super(0, 0, 100, 200);

        rng = new Random();
        setSpeed(20);
        setDimensionsRate(6);
        this.card = card;
        this.fighter = fighter;
    }

    @Override
    public void paintObject(Graphics g, Point dim) {
        Color color;
        if (card.isFaceUp()) {
            switch (card.getColor()) {
                case RED:
                    color = Color.RED; break;
                case GREEN:
                    color = Color.GREEN; break;
                case BLUE:
                    color = Color.BLUE; break;
                default:
                    color = Color.LIGHT_GRAY;
            }
            g.setColor(color);
            g.fillRect(0, 0, dim.x, dim.y);
            g.setColor(Color.BLACK);
            g.drawString(card.getShorthand(), dim.x / 2, dim.y / 2);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, dim.x, dim.y);
        }
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, dim.x, dim.y);

        if (card.getType() == CardType.BLOCK) {
            g.drawRect(3, 3, dim.x - 6, dim.y - 6);
            g.drawRect(6, 6, dim.x - 12, dim.y - 12);
        }
    }

    private void skew() {
        animateRotation(targetRotation + rng.nextGaussian() * SKEW);
    }

    @Override
    public void setLocation(double x, double y) {
        
        if (location.x != x && location.y != y) {
            skew();
        }

        super.setLocation(x, y);
    }

    @Override
    public void animateLocation(double x, double y) {
        
        if (location.x != x && location.y != y) {
            skew();
        }

        super.animateLocation(x, y);
    }
    
    @Override
    public int getZIndex() {
        
        return zIndex + (moving ? 500 : 0);
    }
}

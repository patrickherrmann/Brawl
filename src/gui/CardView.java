package gui;

import brawllogic.Card;
import brawllogic.Fighter;
import deepdish.MovableObject;
import java.awt.Graphics;
import java.awt.Image;
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
        super(0, 0, 100, 162);

        rng = new Random();
        setSpeed(20);
        setDimensionsRate(6);
        this.card = card;
        this.fighter = fighter;
    }

    @Override
    public void paintObject(Graphics g, Point dim) {
        Image image;
        if (card.isFaceUp()) {
            image = fighter.getImage(card.getShorthand());
        } else {
            image = fighter.getImage("back");
        }
        g.drawImage(image, 0, 0, null);
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

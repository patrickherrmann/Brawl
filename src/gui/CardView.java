package gui;

import animation.GameObject;
import animation.Viewport;
import brawllogic.Card;
import brawllogic.Fighter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.RescaleOp;

/**
 * @author Patrick Herrmann
 */
public class CardView extends GameObject {
    
    public static final double MOVEMENT_RATE = 12.5;
    public static final double ROTATION_RATE = 0.04;
    public static final double OPACITY_RATE = 0.02;
    
    private boolean visible = false;
    
    private Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, 100, 200);
    private double rotation = 0;
    private double opacity = 0;
    
    private volatile Rectangle2D.Double targetRect = new Rectangle2D.Double(0, 0, 200, 300);
    private volatile double targetRotation = 0;
    private volatile double targetOpacity = 0;
    
    private static float[] scale = {1.0f, 1.0f, 1.0f, 1.0f };
    private static float[] offsets = new float[4];
    private RescaleOp rop;
    
    private Card card;
    private Fighter fighter;
    
    public CardView(Card card, Fighter fighter) {
        this.card = card;
        this.fighter = fighter;
    }

    @Override
    public void paint(Viewport v) {
        
        if (visible) {
        
            Point centerPixel = v.getPixel(new Point2D.Double(rect.x, rect.y));
            int width = (int) rect.width;
            int height = (int) rect.height;

            //scale[3] = (float) opacity;
            //rop = new RescaleOp(scale, offsets, null);

            Graphics2D g = v.getGraphics();

            //g.translate(centerPixel.x, centerPixel.y);
            //g.rotate(-rotation);

            // draw the image
            g.setColor(card.isFaceUp() ? Color.LIGHT_GRAY : Color.DARK_GRAY);
            g.fillRect(centerPixel.x - width / 2, centerPixel.y - height / 2, width, height);
            g.setColor(Color.DARK_GRAY);
            g.drawRect(centerPixel.x - width / 2, centerPixel.y - height / 2, width, height);
            g.drawString(card.getShorthand(), centerPixel.x, centerPixel.y);

            //g.rotate(rotation);
            //g.translate(-centerPixel.x, -centerPixel.y);
            
        }
    }
    
    private double moveToward(double from, double to, double rate) {
        double diff = to - from;
        if (Math.abs(diff) < rate) {
            return to;
        } else if (diff > 0) {
            return from + rate;
        } else {
            return from - rate;
        }
    }

    @Override
    public void update() {
        
        if (visible) {
        
            double theta = Math.atan2(targetRect.y - rect.y, targetRect.x - rect.x);

            rect.x = moveToward(rect.x, targetRect.x, Math.abs(MOVEMENT_RATE * Math.cos(theta)));
            rect.y = moveToward(rect.y, targetRect.y, Math.abs(MOVEMENT_RATE * Math.sin(theta)));
            rotation = moveToward(rotation, targetRotation, ROTATION_RATE);
            opacity = moveToward(opacity, targetOpacity, OPACITY_RATE);
        }
    }
    
    public void setPosition(double x, double y) {
        if (!visible) {
            rect.x = x;
            rect.y = y;
        }
        targetRect.x = x;
        targetRect.y = y;
    }
    
    public void setRotation(double target) {
        if (!visible) {
            rotation = target;
        }
        this.targetRotation = target;
    }
    
    public void setOpacity(float target) {
        if (!visible) {
            opacity = target;
        }
        this.targetOpacity = target;
    }
    
    public void setVisibility(boolean visible) {
        this.visible = visible;
    }
}

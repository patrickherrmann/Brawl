package animation;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author Patrick Herrmann
 */
public final class Viewport implements Updatable {

    private static final double DEFAULT_BOUNDING_BOX = 100;
    
    private Image canvas;
    private Graphics2D g;

    private Rectangle2D.Double rectangle;
    private double boundingBox = DEFAULT_BOUNDING_BOX;
    private Vector v = new Vector();

    public Viewport(int width, int height) {
        
        // Create a canvas on which to draw before the screen (we're double buffering)
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) canvas.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Set up a rectangle in 2D space that our window renders (independent of rendering size)
        rectangle = new Rectangle2D.Double(0.0, 0.0, width, height);
        center(new Point2D.Double(0.0, 0.0));
    }

    public void center(Point2D.Double point) {
        rectangle.x = point.x - rectangle.width / 2;
        rectangle.y = point.y - rectangle.height / 2;
    }

    public Point2D.Double getCenter() {
        return new Point2D.Double(rectangle.getCenterX(), rectangle.getCenterY());
    }

    public boolean isInBounds(Point2D.Double point) {
        return point.x > rectangle.x - boundingBox &&
                point.x < rectangle.x + rectangle.width + boundingBox &&
                point.y > rectangle.y - boundingBox &&
                point.y < rectangle.y + rectangle.height + boundingBox;
    }

    public Point getPixel(Point2D.Double point) {
        int x = (int) (canvas.getWidth(null) * ((point.x - rectangle.x) / rectangle.width));
        int y = canvas.getHeight(null) - (int) (canvas.getHeight(null) * ((point.y - rectangle.y) / rectangle.height));
        return new Point(x, y);
    }

    public Point2D.Double getPoint(Point pixel) {
        double xratio = (double) pixel.x / canvas.getWidth(null);
        double yratio = (double) (canvas.getHeight(null) - pixel.y) / canvas.getHeight(null);
        return new Point2D.Double(rectangle.x + xratio * rectangle.width, rectangle.y + yratio * rectangle.height);
    }

    @Override
    public void update() {
        rectangle.x += v.x;
        rectangle.y += v.y;
    }

    public Image getImage() {
        return canvas;
    }

    public Graphics2D getGraphics() {
        return g;
    }

    public void setVx(double vx) {
        v.x = vx;
    }

    public void setVy(double vy) {
        v.y = vy;
    }

    public double getVx() {
        return v.x;
    }

    public double getVy() {
        return v.y;
    }
}

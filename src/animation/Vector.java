package animation;

import java.awt.geom.Point2D;

/**
 * @author Patrick Herrmann
 */
public class Vector extends Point2D.Double {

    public Vector() {
        super();
    }

    public Vector(Point2D.Double start, Point2D.Double finish) {
        super();
        x = finish.x - start.x;
        y = finish.y - start.y;
    }

    public Vector(double x, double y) {
        super(x, y);
    }

    public Vector(Vector v) {
        super();
        x = v.x;
        y = v.y;
    }

    public Vector toUnitVector() {
        double magn = getMagnitude();
        return new Vector(x / magn, y / magn);
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector setMagnitude(double magnitude) {
        return toUnitVector().scale(magnitude);
    }

    public Vector plus(Vector v) {
        return new Vector(v.x + x, v.y + y);
    }

    public Point2D.Double addTo(Point2D.Double point) {
        return new Point2D.Double(point.x + x, point.y + y);
    }

    public Vector scale(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public Vector getOpposite() {
        return new Vector(-x, -y);
    }
}

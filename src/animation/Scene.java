package animation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Patrick Herrmann
 */
public final class Scene implements Paintable, Updatable {
    
    // Set of all game objects, synchronized and maintained by z-index for easy rendering
    private final List<GameObject> gameObjects = Collections.synchronizedList(new ArrayList<GameObject>());
    
    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
    
    @Override
    public void update() {
        synchronized (gameObjects) {
            Iterator<GameObject> iter = gameObjects.iterator();
            while (iter.hasNext()) {
                GameObject next = iter.next();
                if (next.isDestroyed()) {
                    iter.remove();
                } else {
                    next.update();
                }
            }
        }
    }

    @Override
    public void paint(Viewport v) {
        // Clear canvas
        v.getGraphics().setColor(Color.WHITE);
        v.getGraphics().fillRect(0, 0, v.getImage().getWidth(null), v.getImage().getHeight(null));
        
        // Paint game objects
        synchronized (gameObjects) {
            Collections.sort(gameObjects);
            for (GameObject gameObject : gameObjects) {
                gameObject.paint(v);
            }
        }
    }
}

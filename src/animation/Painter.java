package animation;

import java.awt.Image;

/**
 * @author Patrick Herrmann
 */
public abstract class Painter extends LoopThread {
    
    private Scene scene;
    private Viewport viewport;
    
    public Painter(Scene scene, Viewport viewport, int fps) {
        super("Painter Thread", fps);
        
        this.scene = scene;
        this.viewport = viewport;
    }

    @Override
    public void performTask() {
        viewport.update();
        scene.paint(viewport);
        sendToScreen(viewport.getImage());
    }
    
    public abstract void sendToScreen(Image buffer);
}

package animation;

/**
 * @author Patrick Herrmann
 */
public final class Animator extends LoopThread {
    
    private Scene scene;
    
    public Animator(Scene scene, int ups) {
        super("Animator Thread", ups);
        
        this.scene = scene;
    }

    @Override
    public void performTask() {
        scene.update();
    }
}

package animation;

/**
 * @author Patrick Herrmann
 */
public abstract class LoopThread extends Thread {
    
    private int rate;
    private volatile boolean running = false;
    
    protected LoopThread(String name, int rate) {
        super(name);
        this.rate = rate;
    }
    
    public int getRate() {
        return rate;
    }
    
    @Override
    public void start() {
        super.start();
        running = true;
    }
    
    @Override
    public void run() {
        long period = 1000 / rate;
        long before, diff;
        while (running) {
            before = System.currentTimeMillis();

            performTask();

            diff = period - (System.currentTimeMillis() - before);
            if (diff > 0) {
                try {
                    Thread.sleep(diff);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }
    
    public abstract void performTask();
}

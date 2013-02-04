package brawllogic;

/**
 * @author Patrick Herrmann
 */
public enum BasePosition {
    HIGH(0), MID(1), LOW(2);
    
    private int index;
    
    private BasePosition(int index) {
        this.index = index;
    }
    
    public int getIndex() {
        return index;
    }
}

package brawllogic;

/**
 * @author Patrick Herrmann
 */
public enum BasePosition {

    HIGH(new int[] {-1, 0, 0}),
    MID (new int[] {0, -1, 1}),
    LOW (new int[] {-1, 1, 2});
    
    private int[] indices;
    
    private BasePosition(int[] indices) {
        this.indices = indices;
    }
    
    public int getIndex(int baseCount) {
        return indices[baseCount - 1];
    }
}

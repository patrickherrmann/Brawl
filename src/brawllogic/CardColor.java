package brawllogic;

/**
 * @author Patrick Herrmann
 */
public enum CardColor {
    RED("r"), GREEN("g"), BLUE("b"), NONE("");
    
    private String shorthand;
    
    private CardColor(String shorthand) {
        this.shorthand = shorthand;
    }
    
    public String getShorthand() {
        return shorthand;
    }
}

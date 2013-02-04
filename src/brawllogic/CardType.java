package brawllogic;

/**
 * @author Patrick Herrmann
 */
public enum CardType {
    
    HIT(CardCategory.STACKER, "h"),
    SMASH(CardCategory.STACKER, "s"),
    BLOCK(CardCategory.STACKER, "b"),
    PRESS(CardCategory.STACKER, "p"),
    BASE(CardCategory.BASE, "base"),
    CLEAR(CardCategory.ACTION, "c"),
    FREEZE(CardCategory.ACTION, "f");
    
    private CardCategory category;
    private String shorthand;
    
    private CardType(CardCategory category, String shorthand) {
        this.category = category;
        this.shorthand = shorthand;
    }
    
    public CardCategory getCategory() {
        return category;
    }
    
    public String getShorthand() {
        return shorthand;
    }
}

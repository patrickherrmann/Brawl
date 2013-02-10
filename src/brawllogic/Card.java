package brawllogic;

/**
 * @author Patrick Herrmann
 */
public final class Card {
    
    private CardType type;
    private CardColor color;
    private Player owner;
    private boolean faceUp;
    
    public Card(Player owner, CardType type) {
        this.owner = owner;
        this.type = type;
        this.color = CardColor.NONE;
        faceUp = false;
    }
    
    public Card(Player owner, CardType type, CardColor color) {
        this.owner = owner;
        this.type = type;
        this.color = color;
    }
    
    public CardType getType() {
        return type;
    }
    
    public CardColor getColor() {
        return color;
    }
    
    public Player getOwner() {
        return owner;
    }
    
    @Override
    public String toString() {
        
        StringBuilder name = new StringBuilder();
        
        if (color != CardColor.NONE) {
            name.append(color);
            name.append(" ");
        }
        
        name.append(type);
        
        return name.toString();
    }
    
    public String getShorthand() {
        
        return color.getShorthand() + type.getShorthand();
    }
    
    public boolean isFaceUp() {
        return faceUp;
    }
    
    public void flip() {
        faceUp = !faceUp;
    }
}

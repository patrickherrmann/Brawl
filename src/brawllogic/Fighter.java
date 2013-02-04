package brawllogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @author Patrick Herrmann
 */
public class Fighter {
    
    private String theme;
    private String name;
    
    public Fighter(String theme, String name) {
        this.theme = theme;
        this.name = name;
    }
    
    private void handleLine(String line, Player owner, Stack<Card> deck) {
        
        StringTokenizer st;
        
        String cardTypeToken;
        CardType cardType;
        
        String cardCountToken;
        int cardCount;
        
        String cardColorToken;
        CardColor cardColor = CardColor.NONE;
        
        st = new StringTokenizer(line);
        cardTypeToken = st.nextToken();
        
        try {
            cardType = CardType.valueOf(cardTypeToken);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid card type '" + cardTypeToken + "'.");
        }
        
        if (!st.hasMoreTokens())
            throw new RuntimeException("Missing card count in manifest file.");
        
        cardCountToken = st.nextToken();
        
        try {
            cardCount = Integer.parseInt(cardCountToken);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid card count '" + cardCountToken + "'.");
        }
        
        if (st.hasMoreTokens()) {
            
            cardColorToken = st.nextToken();
            
            try {
                cardColor = CardColor.valueOf(cardColorToken);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid card color '" + cardColorToken + "'.");
            }
        }
        
        for (int i = 0; i < cardCount; i++)
            deck.push(new Card(owner, cardType, cardColor));
    }
    
    public void arrangeDeck(Player player, Stack<Card> deck) {
        Collections.shuffle(deck);
        
        for (int i = 0; i < 3; i++)
            deck.add(0, new Card(player, CardType.FREEZE));
    }
    
    public Stack<Card> loadDeck(Player player) {
        
        File file = new File("themes/" + theme + "/" + name + "/manifest");
        Stack<Card> deck = new Stack<Card>();
        
        try {
            
            Scanner scanner = new Scanner(file);
            String line;
            
            while (scanner.hasNext()) {
                
                line = scanner.nextLine();
                
                if (!line.isEmpty() && !line.startsWith("#"))
                    handleLine(line, player, deck);
                
            }
            
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        
        arrangeDeck(player, deck);
        
        return deck;
    }
    
    public String getName() {
        return name;
    }
}

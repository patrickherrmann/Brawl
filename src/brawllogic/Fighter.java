package brawllogic;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;

/**
 * @author Patrick Herrmann
 */
public final class Fighter {
    
    public static List<String> getAvailableThemes() {
        
        return listDirectories("themes");
    }
    
    public static List<String> getAvailableFighters(String theme) {
        
        return listDirectories("themes/" + theme);
    }
    
    private static List<String> listDirectories(String directory) {
        
        List<String> subs = new ArrayList<String>();
        
        File dir = new File(directory);
        
        for (File file : dir.listFiles())
            if (file.isDirectory())
                subs.add(file.getName());
        
        return subs;
    }
    
    private String theme;
    private String name;
    private Map<String, Image> images;
    
    public Fighter(String theme, String name) {
        this.theme = theme;
        this.name = name;
        images = new HashMap<String, Image>();
        
        loadDeckImages();
    }
    
    private File getDir() {
        return new File("themes/" + theme + "/" + name);
    }
    
    private File[] getImageFiles() {
        return getDir().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg");
            }
        });
    }
    
    private void loadDeckImages() {
        for (File file : getImageFiles()) {
            String filename = file.getName();
            filename = filename.substring(0, filename.lastIndexOf('.'));
            Image image;
            try {
                image = ImageIO.read(file);
            } catch (IOException ex) {
                throw new RuntimeException("Couldn't load deck images for " + name);
            }
            images.put(filename, image);
        }
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
    
    private void arrangeDeck(Stack<Card> deck) {
        
        List<Card> freezes = new ArrayList<Card>();
        Card initialBase = null;
        
        Iterator<Card> iterator = deck.iterator();
        
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getType() == CardType.FREEZE) {
                iterator.remove();
                freezes.add(card);
            } else if (card.getType() == CardType.BASE) {
                initialBase = card;
            }
        }
        
        if (initialBase == null)
            throw new RuntimeException("There must be at least one base in the deck.");
        
        Collections.shuffle(deck);
        
        deck.remove(initialBase);
        
        deck.push(initialBase);
        deck.addAll(0, freezes);
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
        
        arrangeDeck(deck);
        
        return deck;
    }
    
    public String getName() {
        return name;
    }

    public Image getImage(String shorthand) {
        return images.get(shorthand);
    }
}

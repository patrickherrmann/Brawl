/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author patrick
 */
public class KeyMap {

    public static KeyMap getDefaultKeyMap() {

        Map<Character, Move> moves = new HashMap<Character, Move>();

        moves.put('w', new Move(Player.LEFT, Player.LEFT, BasePosition.HIGH));
        moves.put('e', new Move(Player.LEFT, Player.RIGHT, BasePosition.HIGH));
        moves.put('s', new Move(Player.LEFT, Player.LEFT, BasePosition.MID));
        moves.put('d', new Move(Player.LEFT, Player.RIGHT, BasePosition.MID));
        moves.put('x', new Move(Player.LEFT, Player.LEFT, BasePosition.LOW));
        moves.put('c', new Move(Player.LEFT, Player.RIGHT, BasePosition.LOW));

        moves.put('o', new Move(Player.RIGHT, Player.LEFT, BasePosition.HIGH));
        moves.put('p', new Move(Player.RIGHT, Player.RIGHT, BasePosition.HIGH));
        moves.put('l', new Move(Player.RIGHT, Player.LEFT, BasePosition.MID));
        moves.put(';', new Move(Player.RIGHT, Player.RIGHT, BasePosition.MID));
        moves.put('.', new Move(Player.RIGHT, Player.LEFT, BasePosition.LOW));
        moves.put('/', new Move(Player.RIGHT, Player.RIGHT, BasePosition.LOW));

        Map<Player, Character> drawKeys = new EnumMap(Player.class);

        drawKeys.put(Player.LEFT, 'z');
        drawKeys.put(Player.RIGHT, ',');

        return new KeyMap(moves, drawKeys);
    }
    
    private Map<Character, Move> moves;
    private Map<Player, Character> drawKeys;

    public KeyMap(Map<Character, Move> moves, Map<Player, Character> drawKeys) {
        this.moves = moves;
        this.drawKeys = drawKeys;
    }

    public Map<Character, Move> getMoves() {
        return moves;
    }

    public char getDrawKey(Player player) {
        return drawKeys.get(player);
    }
}

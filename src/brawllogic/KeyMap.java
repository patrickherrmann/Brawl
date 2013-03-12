/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package brawllogic;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author patrick
 */
public class KeyMap {

    public static KeyMap getDefaultKeyMap(Player player) {

        Map<Character, Move> moves = new HashMap<Character, Move>();
        char drawKey;

        if (player == Player.LEFT) {
            moves.put('w', new Move(Player.LEFT, Player.LEFT, BasePosition.HIGH));
            moves.put('e', new Move(Player.LEFT, Player.RIGHT, BasePosition.HIGH));
            moves.put('s', new Move(Player.LEFT, Player.LEFT, BasePosition.MID));
            moves.put('d', new Move(Player.LEFT, Player.RIGHT, BasePosition.MID));
            moves.put('x', new Move(Player.LEFT, Player.LEFT, BasePosition.LOW));
            moves.put('c', new Move(Player.LEFT, Player.RIGHT, BasePosition.LOW));
            drawKey = 'z';
        } else {
            moves.put('o', new Move(Player.RIGHT, Player.LEFT, BasePosition.HIGH));
            moves.put('p', new Move(Player.RIGHT, Player.RIGHT, BasePosition.HIGH));
            moves.put('l', new Move(Player.RIGHT, Player.LEFT, BasePosition.MID));
            moves.put(';', new Move(Player.RIGHT, Player.RIGHT, BasePosition.MID));
            moves.put('.', new Move(Player.RIGHT, Player.LEFT, BasePosition.LOW));
            moves.put('/', new Move(Player.RIGHT, Player.RIGHT, BasePosition.LOW));
            drawKey = ',';
        }

        return new KeyMap(moves, drawKey);
    }
    
    private Map<Character, Move> moves;
    private char drawKey;

    public KeyMap(Map<Character, Move> moves, char drawKey) {
        this.moves = moves;
        this.drawKey = drawKey;
    }

    public Map<Character, Move> getMoves() {
        return moves;
    }

    public char getDrawKey() {
        return drawKey;
    }
}

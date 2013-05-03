package brawllogic;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author patrick
 */
public class KeyMap {

    public static Map<Character, Move> getDefaultKeyMap(Player player) {

        Map<Character, Move> moves = new HashMap<Character, Move>();

        if (player == Player.LEFT) {
            moves.put('w', new PlayCardAction(Player.LEFT, Player.LEFT, BasePosition.HIGH));
            moves.put('e', new PlayCardAction(Player.LEFT, Player.RIGHT, BasePosition.HIGH));
            moves.put('s', new PlayCardAction(Player.LEFT, Player.LEFT, BasePosition.MID));
            moves.put('d', new PlayCardAction(Player.LEFT, Player.RIGHT, BasePosition.MID));
            moves.put('x', new PlayCardAction(Player.LEFT, Player.LEFT, BasePosition.LOW));
            moves.put('c', new PlayCardAction(Player.LEFT, Player.RIGHT, BasePosition.LOW));
            moves.put('z', new DrawAction(player));
        } else {
            moves.put('o', new PlayCardAction(Player.RIGHT, Player.LEFT, BasePosition.HIGH));
            moves.put('p', new PlayCardAction(Player.RIGHT, Player.RIGHT, BasePosition.HIGH));
            moves.put('l', new PlayCardAction(Player.RIGHT, Player.LEFT, BasePosition.MID));
            moves.put(';', new PlayCardAction(Player.RIGHT, Player.RIGHT, BasePosition.MID));
            moves.put('.', new PlayCardAction(Player.RIGHT, Player.LEFT, BasePosition.LOW));
            moves.put('/', new PlayCardAction(Player.RIGHT, Player.RIGHT, BasePosition.LOW));
            moves.put(',', new DrawAction(player));
        }
        
        return moves;
    }
}

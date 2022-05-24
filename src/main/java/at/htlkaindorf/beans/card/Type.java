package at.htlkaindorf.beans.card;

import java.util.HashMap;
import java.util.Map;

public enum Type {
    ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
    REVERSE(10), BLOCK(11), PLUS_TWO(12), SKIP(13),
    CHOOSE_COLOR(14), PLUS_FOUR(15);

    private final int value;
    private static  Map<Integer, Type> map = new HashMap<>();

    Type(int i) {
        this.value = i;
    }

    static {
        for (Type type : Type.values()) {
            map.put(type.value, type);
        }
    }


    public static Type valueOf(int value) {
        return (Type) map.get(value);
    }
    
}

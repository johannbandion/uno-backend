package at.htlkaindorf.beans;

import java.util.HashMap;
import java.util.Map;

public enum Color {
    GREEN(0), RED(1), BLUE(2), YELLOW(3), BLACK(4);

    private final int value;
    private static Map<Integer, Color> map = new HashMap<>();


    Color(int value) {
        this.value = value;
    }


    static {
        for (Color color : Color.values()) {
            map.put(color.value, color);
        }
    }


    public static Color valueOf(int value) {
        return (Color) map.get(value);
    }
}

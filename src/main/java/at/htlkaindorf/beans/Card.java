package at.htlkaindorf.beans;

import java.util.Objects;

public class Card {

    private Type type;
    private Color color;

    public Card(Type type, Color color) {
        if ((type == Type.CHOOSE_COLOR || type == Type.PLUS_FOUR) && color != Color.BLACK ) {
            throw new RuntimeException("This card type can't have this color");
        }
        this.type = type;
        this.color = color;
    }

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (this.color != Color.BLACK) {
            throw new RuntimeException("Can't change color of card with this color: " + this.color.toString());
        }
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return type == card.type && color == card.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color);
    }
}

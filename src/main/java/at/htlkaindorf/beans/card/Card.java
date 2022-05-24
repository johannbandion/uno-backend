package at.htlkaindorf.beans.card;

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
        this.color = color;
    }

    @Override
    public String toString() {
        return "Card{" +
                "type=" + type +
                ", color=" + color +
                '}';
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

    public Card() {
    }

    public void setType(Type type) {
        this.type = type;
    }
}

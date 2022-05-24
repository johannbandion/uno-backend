package at.htlkaindorf.beans;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private Session session;
    private final String name;

    private List<Card> cards = new ArrayList<>();

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Player(Session session, String name) {
        this.session = session;
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(session, player.session) && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, name);
    }
}

package at.htlkaindorf.beans;

import javax.websocket.Session;
import java.util.List;
import java.util.Objects;

public class Player {
    private Session session;
    private String name;

    private List<Card> cards;

    public Player(Session session, String name) {
        this.session = session;
        this.name = name;
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

package at.htlkaindorf;

import at.htlkaindorf.beans.Player;

import javax.ws.rs.BadRequestException;
import java.util.Set;

public class Room {
    private String roomName;
    private Set<Player> players;

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public void addPlayer(Player player) {
        if (players.size() > 1) {
            throw  new BadRequestException();
        }
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}

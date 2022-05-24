package at.htlkaindorf.endpoints;

import at.htlkaindorf.bl.Room;
import at.htlkaindorf.beans.Player;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.BadRequestException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@ServerEndpoint("/uno/{room}/{name}")
@ApplicationScoped
public class WebSocketEndpoint {

    Map<String, Room> rooms = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("room") String roomName, @PathParam("name") String name) {
        System.out.println("onOpen> started" + session);

        Player newPlayer = new Player(session, name);
        Room room;
        if( rooms.containsKey(roomName)) {
            room = rooms.get(roomName);
            room.addPlayer(newPlayer);
        } else {
            room = new Room(roomName);
            room.addPlayer(newPlayer);
            rooms.put(roomName, room);
        }
        session.getAsyncRemote().sendText("Hallo");
        multicastGamestateRoom(room);
        System.out.println("onOpen> finished" + session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("room") String roomName, @PathParam("name") String name) {

        Room room = rooms.get(roomName);
        room.removePlayer(new Player(session, name));

        System.out.println("onClose> " + session);
    }

    @OnError
    public void onError(Session session, @PathParam("room") String room, @PathParam("name") String name, Throwable throwable) {

        System.out.println("onError> " + room + ": " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("room") String roomName, @PathParam("name") String playerName) {
        System.out.println("onMessage> " + roomName + ":" + playerName + ": "+ message);
        if (!rooms.containsKey(roomName)) {
            throw new BadRequestException("Room doesn't exitst");
        }

        Room room = rooms.get(roomName);
        multicastGamestateRoom(room);
    }

    public void multicastGamestateRoom(Room room) {
        List<Player> players = room.getPlayers();
        System.out.println("Tried to perform multicast");
        for (Player player : players) {
            player.getSession().getAsyncRemote().sendObject(room.getGameState(player), result -> {
                if (result.getException() != null) {
                    System.out.println("Unable to Multicast gamestate in room message: " + result.getException());
                }
            });
        }
        players.get(0).getSession().getAsyncRemote().sendText(room.getGameState(players.get(0)).toString());


    }
}

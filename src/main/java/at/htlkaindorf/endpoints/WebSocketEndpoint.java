package at.htlkaindorf.endpoints;

import at.htlkaindorf.beans.message.Request;
import at.htlkaindorf.bl.Room;
import at.htlkaindorf.beans.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        multicastGamestateRoom(room);
        System.out.println("onOpen>" + session + ":" + roomName + ":" + name);
    }

    @OnClose
    public void onClose(Session session, @PathParam("room") String roomName, @PathParam("name") String name) {
        // TODO if one player leaves gamestate should be changed to down and he should reseave a new hand
        Room room = rooms.get(roomName);
        room.removePlayer(new Player(session, name));

        System.out.println("onClose> " + session);
        if(room.getPlayers().size() == 0) {
            rooms.remove(roomName);
            System.out.println("onClose> closed Room: " + roomName);
        }
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
        ObjectMapper mapper = new ObjectMapper();

        Request request = new Request();
        try {
            request = mapper.readValue(message, Request.class);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Couldn't map the message to Request object");
        }
        switch (request.getEvent()) {
            case LAYCARD:
                if (request.getCard() == null) {
                    throw new BadRequestException("LAYCARD: doesn't contain card");
                }
                room.layCard(room.getPlayer(playerName), request.getCard());
                break;
            case WITHDRAWCARD:
                room.withDrawCard(room.getPlayer(playerName));
                break;
            case GETSTATE:
                break;
            case CONNECT:
                throw new BadRequestException("Already connected");
            default:
                throw new BadRequestException("Unknown request Type");
        }
        multicastGamestateRoom(room);
    }

    public void multicastGamestateRoom(Room room) {
        List<Player> players = room.getPlayers();
        System.out.println("Tried to perform multicast");
        System.out.println("achtung length of players: " + players.size());
        for (Player player : players) {
            player.getSession().getAsyncRemote().sendText(room.getGameState(player).toString(), result -> {
                if (result.getException() != null) {
                    System.out.println("Unable to Multicast gamestate in room message: " + result.getException());
                }
            });
        }
    }
}

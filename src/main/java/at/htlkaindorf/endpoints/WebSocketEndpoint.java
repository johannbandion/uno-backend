package at.htlkaindorf.endpoints;

import at.htlkaindorf.Room;
import at.htlkaindorf.beans.Player;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@ServerEndpoint("/uno/{room}/{name}")
@ApplicationScoped
public class WebSocketEndpoint {

    Map<String, Room> rooms = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("room") String roomName, @PathParam("name") String name) {
//        Player newPlayer = new Player(session, name);
//
//        if( rooms.containsKey(roomName)) {
//            rooms.get(roomName).addPlayer(newPlayer);
//            return;
//        }
//
//        Room room = rooms.put(roomName, new Room(roomName));
//
//        room.addPlayer(newPlayer);

        System.out.println("onOpen> " + session);
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
    public void onMessage(String message, @PathParam("room") String room, @PathParam("name") String name) {
        System.out.println("onMessage> " + room + ":" + name + ": "+ message);

    }
}

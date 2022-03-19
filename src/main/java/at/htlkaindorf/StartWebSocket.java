package at.htlkaindorf;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

@ServerEndpoint("/uno/{room}/{name}")
@ApplicationScoped
public class StartWebSocket {



    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room, @PathParam("name") String name) {
        System.out.println("onOpen> " + session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("room") String room, @PathParam("name") String name) {
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

package at.htlkaindorf.beans.message;

import at.htlkaindorf.beans.card.Card;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Request {
    private EventType event;
    private Card card;

    public Request(EventType event, Card card) {
        this.event = event;
        this.card = card;
    }

    public Request(EventType event) {
        this.event = event;
    }

    public Request() {
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public static void main(String[] args) {
        Request request = new Request(EventType.WITHDRAWCARD, null);

        ObjectMapper mapper = new ObjectMapper();

        String s = null;
        try {
            s = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(s);

        Request request1 = null;

        try {
            request1 = mapper.readValue(s, Request.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(request1);
    }

    @Override
    public String toString() {
        return "Request{" +
                "event=" + event +
                ", card=" + card +
                '}';
    }
}

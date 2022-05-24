package at.htlkaindorf.zold;

import at.htlkaindorf.beans.card.Card;
import at.htlkaindorf.beans.card.Color;
import at.htlkaindorf.util.Deck;

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game {
    //Closed stack of cards
    public Stack<Card> stackOfCards;
    public List<Card>[] players = new ArrayList[2];
    public int nextplayer = 0;
    public Card openCard;

    public Game() {
        this.stackOfCards = Deck.getNewStackOfCards();
        for (int i = 0; i < 2; i++) {
            List<Card> player = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                player.add(stackOfCards.pop());
            }
            players[i] = player;
        }
        openCard = stackOfCards.pop();
    }

    public void currentPlayerTriesToPlaceCard(Card card) {
        if (!canPutCardOnStack(card)) {
            throw new BadRequestException("Couldn't lay card on stack");
        }
        openCard = card;
        switchPlayers();
    }

    public void drawCard(int playerToDrawCards) {
        if (stackOfCards.isEmpty()) {
            stackOfCards = Deck.getNewStackOfCards();
        }
        players[playerToDrawCards].add(stackOfCards.pop());
        switchPlayers();
    }

    public void switchPlayers() {
        if (nextplayer == 0) {
            nextplayer = 1;
        } else {
            nextplayer = 0;
        }
    }

    public boolean canPutCardOnStack(Card card) {
        // schwarze Karten kann man immer auf den stapel legen
        if (card.getColor() == Color.BLACK ) {
            return true;
        }
        // selbe farbe
        if (card.getColor().equals(openCard.getColor())) {
            return true;
        }
        // selber typ
        if (card.getType().equals(openCard.getType())) {
            return true;
        }
        return false;
    }



//    public static void main(String[] args) {
//
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Game game = new Game();
//
//        String s = null;
//        try {
//            s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
//        } catch (JsonProcessingException e) {
//            System.out.println("Faileded twoo mab");
//        }
//
//        System.out.println(s);
//
//        System.out.println(game.openCard.getType());
//
//
//    }
}


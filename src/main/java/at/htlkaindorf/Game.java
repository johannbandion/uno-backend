package at.htlkaindorf;

import at.htlkaindorf.beans.Card;
import at.htlkaindorf.beans.Color;
import at.htlkaindorf.beans.Deck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game {

    public Stack<Card> stackOfCards;
    public List<Card>[] players = new ArrayList[2];
    public int nextPlayer = 0;
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

    public void currentPlayerTriesToLayCard(Card card) {
        if (!canPutCardOnStack(card)) {
            throw new BadRequestException("Couldn't lay card on stack");
        }

    }

    public void drawCard(int playerToDrawCards) {
        if (stackOfCards.isEmpty()) {
            stackOfCards = Deck.getNewStackOfCards();
        }
        players[playerToDrawCards].add(stackOfCards.pop());
    }

    public boolean canPutCardOnStack(Card card) {
        if (card.getColor() == Color.BLACK) {
            return true;
        }
        if (card.getColor().equals(openCard.getColor())) {
            return true;
        }
        if (card.getType().equals(openCard.getType())) {
            return true;
        }
        return false;
    }



    public static void main(String[] args) {


        ObjectMapper mapper = new ObjectMapper();

        Game game = new Game();

        String s = null;
        try {
            s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
        } catch (JsonProcessingException e) {
            System.out.println("Faileded twoo mab");
        }

        System.out.println(s);

        System.out.println(game.openCard.getType());


    }
}


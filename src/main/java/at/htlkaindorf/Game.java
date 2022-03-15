package at.htlkaindorf;

import at.htlkaindorf.beans.Card;
import at.htlkaindorf.beans.Color;
import at.htlkaindorf.beans.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static void main(String[] args) {
        Game game = new Game();
        game.initGame();
    }

    public void initGame() {
        List<Card> cards = new ArrayList<>();
        // every card exists two times
        for (int k = 0; k < 2; k++) {
            // for every number card
            for (int i = 0; i < 13; i++) {
                // Zero only exists one time per color
                if (k == 1 && i == 0) continue;
                // For the four different colors
                for (int j = 0; j < 4; j++) {
                    cards.add(new Card(
                            Type.valueOf(i),
                            Color.valueOf(j)
                    ));
                }
            }
        }


        for (int j = 13; j < 15; j++) {
            for (int i = 0; i < 5; i++) {
                cards.add(new Card(
                        Type.valueOf(j),
                        Color.BLACK
                ));
            }
        }

        ObjectMapper mapper = new ObjectMapper();

        String s = null;
        try {
            s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cards);
        } catch (JsonProcessingException e) {
            System.out.println("Failed two map");
        }

        System.out.println(s);

        System.out.println(cards.size());
    }
}


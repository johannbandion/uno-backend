package at.htlkaindorf.util;

import at.htlkaindorf.beans.card.Card;
import at.htlkaindorf.beans.card.Color;
import at.htlkaindorf.beans.card.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    static final List<Card> cards = new ArrayList<>();

    static {
        // every card exists two times
        for (int k = 0; k < 2; k++) {
            // for every number card
            for (int i = 0; i < 14; i++) {
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

        for (int j = 14; j < 16; j++) {
            for (int i = 0; i < 5; i++) {
                cards.add(new Card(
                        Type.valueOf(j),
                        Color.BLACK
                ));
            }
        }
    }

    public static Stack<Card> getNewStackOfCards() {
        List<Card> list = new ArrayList<>(cards);
        Collections.shuffle(list);
        Stack<Card> stack = new Stack<>();
        stack.addAll(list);
        return stack;
    }
}

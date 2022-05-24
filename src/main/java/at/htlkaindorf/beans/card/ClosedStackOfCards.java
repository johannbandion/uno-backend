package at.htlkaindorf.beans.card;

import at.htlkaindorf.util.Deck;

import java.util.Stack;

public class ClosedStackOfCards {
    private Stack<Card> cardStack;

    public ClosedStackOfCards() {
        cardStack = Deck.getNewStackOfCards();
    }

    public Card pop() {
        if (cardStack.size() > 0) {
            cardStack = Deck.getNewStackOfCards();
        }
        return cardStack.pop();
    }
}

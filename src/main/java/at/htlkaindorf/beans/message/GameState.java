package at.htlkaindorf.beans.message;

import at.htlkaindorf.beans.card.Card;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private boolean gameready = false;
    private boolean isPlayerTurn = false;
    private final String playerName;
    private List<Card> playerHand = new ArrayList<>();
    private Card openCard = null;
    private String opponentName;
    private int opponentHandLength = 0;

    public void setGameready(boolean gameready) {
        this.gameready = gameready;
    }

    public void setPlayerTurn(boolean playerTurn) {
        isPlayerTurn = playerTurn;
    }

    public void setPlayerHand(List<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public Card getOpenCard() {
        return openCard;
    }

    public void setOpenCard(Card openCard) {
        this.openCard = openCard;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public void setOpponentHandLength(int opponentHandLength) {
        this.opponentHandLength = opponentHandLength;
    }

    public boolean isGameready() {
        return gameready;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public int getOpponentHandLength() {
        return opponentHandLength;
    }



    public GameState(boolean gameready, boolean isPlayerTurn, String playerName, List<Card> playerHand, String opponentName, int opponentHandLength, Card openCard) {
        this.gameready = gameready;
        this.isPlayerTurn = isPlayerTurn;
        this.playerName = playerName;
        this.playerHand = playerHand;
        this.opponentName = opponentName;
        this.opponentHandLength = opponentHandLength;
        this.openCard = openCard;

    }

    @Override
    public String toString() {

        ObjectMapper mapper = new ObjectMapper();

        String s = "Failed to map";
        try {
            s = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            System.out.println("Failed to map");
        }

        return s;
    }
}

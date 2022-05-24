package at.htlkaindorf.beans.message;

import at.htlkaindorf.beans.card.Card;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private boolean Gameready = false;
    private boolean isPlayerTurn = false;
    private final String playerName;
    private List<Card> playerHand = new ArrayList<>();

    public boolean isGameready() {
        return Gameready;
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

    private String opponentName;
    private int opponentHandLength;

    public GameState(boolean gameready, boolean isPlayerTurn, String playerName, List<Card> playerHand, String opponentName, int opponentHandLength) {
        Gameready = gameready;
        this.isPlayerTurn = isPlayerTurn;
        this.playerName = playerName;
        this.playerHand = playerHand;
        this.opponentName = opponentName;
        this.opponentHandLength = opponentHandLength;
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

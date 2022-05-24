package at.htlkaindorf.bl;

import at.htlkaindorf.beans.*;
import at.htlkaindorf.beans.card.Card;
import at.htlkaindorf.beans.card.ClosedStackOfCards;
import at.htlkaindorf.beans.message.GameState;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import java.util.*;

public class Room {
    private final String roomName;
    private List<Player> players = new ArrayList<>();
    private Player currentPlayer;
    public ClosedStackOfCards closedStackOfCards = new ClosedStackOfCards();
    public Stack<Card> openStackOfCards = new Stack<>();

    public Room(String roomName) {
        this.roomName = roomName;
        openStackOfCards.push(closedStackOfCards.pop());
    }
    public GameState getGameState(Player player) {
        Player playerInList;
        int index;
        try {
            index = players.indexOf(player);
            playerInList = players.get(players.indexOf(player));
        } catch (IndexOutOfBoundsException e) {
            throw new BadRequestException("Couldn't get Gamestate");
        }
        GameState gameState;
        if (players.size() > 1) {
            Player opponent = players.get((index == 0) ? 1 : 0);
            gameState = new GameState(true, currentPlayer.equals(player), playerInList.getName(), playerInList.getCards(), opponent.getName(), opponent.getCards().size() );
        } else {
            gameState = new GameState(false, false, playerInList.getName(), playerInList.getCards(),null, 0 );
        }
//boolean gameready, boolean isPlayerTurn, String playerName, List<Card> playerHand, String opponentName, int opponentHandLength) {
        System.out.println("II neeed statement");
        return gameState;
    }
    public void addPlayer(Player player) {
        if (players.size() > 1) {
            throw  new BadRequestException("Room is full");
        }
        if (players.size() > 0 && currentPlayer == null) {
            currentPlayer = players.get(0);
            if (players.get(0).getName().equals(player.getName())) {
                throw new BadRequestException("Player with same name in room");
            }
        }
        for (int i = 0; i < 7; i++) {
             player.addCard(closedStackOfCards.pop());
        }
        players.add(player);
    }

    public Player getPlayer(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        throw new InternalServerErrorException("couldn't return player");
    }


    public void removePlayer(Player player) {
        if (player.equals(currentPlayer)) {
            currentPlayer = null;
        }
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
    }
}

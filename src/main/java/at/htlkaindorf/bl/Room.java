package at.htlkaindorf.bl;

import at.htlkaindorf.beans.Player;
import at.htlkaindorf.beans.card.Card;
import at.htlkaindorf.beans.card.ClosedStackOfCards;
import at.htlkaindorf.beans.card.Color;
import at.htlkaindorf.beans.message.GameState;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
            gameState = new GameState(true, currentPlayer.equals(player), playerInList.getName(), playerInList.getCards(), opponent.getName(), opponent.getCards().size(), openStackOfCards.peek());
        } else {
            gameState = new GameState(false, false, playerInList.getName(), playerInList.getCards(), null, 0, null);
        }
//boolean gameready, boolean isPlayerTurn, String playerName, List<Card> playerHand, String opponentName, int opponentHandLength) {
        System.out.println("II neeed statement");
        return gameState;
    }

    public void addPlayer(Player player) {
        if (players.size() > 1) {
            throw new BadRequestException("Room is full");
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

    private void throwIfGameIsntReady() {
        if (players.size() < 2) {
            throw new BadRequestException("Game isn't Ready");
        }
    }

    private void throwIfIsntPlayersTurn(Player player) {
        if (player != currentPlayer) {
            throw new BadRequestException("Isn't players turn");
        }
    }

    // Game logic

    private void changeCurrentPlayer() {
        throwIfGameIsntReady();
        if(currentPlayer == null) {
            currentPlayer = players.get(0);
        }
        if (currentPlayer == players.get(0)) {
            currentPlayer = players.get(1);
        } else if (currentPlayer == players.get(1)) {
            currentPlayer = players.get(0);
        } else {
            System.out.println("Room.changeCurrentPlayer: Really shouldn't happen!");
        }
    }

    public void layCard(Player player, Card card) {
        throwIfGameIsntReady();
        throwIfIsntPlayersTurn(player);
        if (!canPutCardOnStack(card)) {
            throw new BadRequestException("Can't put this card on Stack");
        }
        player.removeCard(card);
        openStackOfCards.push(card);
        changeCurrentPlayer();
    }

    public void withDrawCard(Player player) {
        throwIfGameIsntReady();
        throwIfIsntPlayersTurn(player);
        player.addCard(closedStackOfCards.pop());
        changeCurrentPlayer();
    }

    public boolean canPutCardOnStack(Card card) {
        // schwarze Karten kann man immer auf den stapel legen
        if (card.getColor() == Color.BLACK ) {
            return true;
        }
        // selbe farbe
        if (card.getColor().equals(openStackOfCards.peek().getColor())) {
            return true;
        }
        // selber typ
        if (card.getType().equals(openStackOfCards.peek().getType())) {
            return true;
        }
        return false;
    }
}

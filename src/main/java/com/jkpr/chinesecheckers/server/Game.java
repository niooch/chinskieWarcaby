package com.jkpr.chinesecheckers.server;

import java.util.HashMap;

/**
 * Represents the game of Chinese checkers.
 * <p>
 * The {@code Game} class manages the players and the board. It also processes the moves made during the game,
 * validating them against the current state of the board.
 * </p>
 * <p>
 * The class uses a {@code HashMap} to store players, where the key is an integer representing the player's ID.
 * The game board is managed by an instance of {@code AbstractBoard}.
 * </p>
 */
public class Game {

    /**
     * A map of players in the game, keyed by player ID.
     * <p>
     * The {@code HashMap} holds player objects, where the key is the player's unique ID and the value is
     * an instance of {@code AbstractPlayer} representing the player.
     * </p>
     */
    //TODO dodawać graczy do tej hashmapy
    // wydaje mi się że lepiej będzie to zrobić zaraz przy inicjacji game, a potem przypisywać tylko kolejnym
    // klientom obiekty
    private int playersCount=0;
    private int maxPlayers;

    /** The game board. */
    private AbstractBoard board;
    private AbstractRules rules;

    /**
     * Processes the next move in the game.
     * <p>
     * This method receives a string message representing the move, and it is responsible for extracting
     * the necessary information such as the player making the move and the starting and ending positions
     * on the board. The move is then validated by calling {@code isValidMove} on the {@code AbstractBoard}.
     * </p>
     *
     * <p><b>Note:</b> The player is assumed to be the one currently active in the game.</p>
     *
     */
    public void nextMove(Move move,Player player) {
        //TODO dodać zparsowane dane do obróbki przez funkcję "isValidMove" (gracz niekonieczie musi tu być
        // będzie można wyjąć go jako będącego w stanie active

        rules.isValidMove(board,player,move.getStart(),move.getEnd());
        //TODO tak naprawdę kolejnym krokiem tutaj będzie coś w stylu board.move albo player.move
        // (chyba to drugie lepsze) jakoś muszę jeszcze rozwiązać problem instancji pionków i tego kto i gdzie
        // ma mieć do nich dostęp.
        // Dalej trzeba będzie zmienić stan graczy, ale tego jeszcze nie zaimplementowałem
        // I na koniec zostaje już tylko sformułować wnioski i to raczej tyle jeśli chodzi o logikę gry
    }

    /**
     * Sets the game board.
     * <p>
     * This method assigns a new {@code AbstractBoard} to the game, which will be used to validate moves
     * and track the positions of the game pieces.
     * </p>
     *
     * @param board the {@code AbstractBoard} to set for the game
     */
    public void setBoard(AbstractBoard board) {
        this.board = board;
    }

    /**
     * Sets the players for the game.
     * <p>
     * This method assigns a {@code HashMap} of players to the game. The map should contain player IDs as keys
     * and {@code AbstractPlayer} objects as values, representing all players participating in the game.
     * </p>
     *
     */
    public void setRules(AbstractRules rules){this.rules=rules;}
    public Player join(){
        return board.getPlayers().get(playersCount++);
    }
}


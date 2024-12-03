package com.jkpr.chinesecheckers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the abstract concept of a game board.
 * <p>
 * The {@code AbstractBoard} class serves as a base for defining game boards in different variants of the game.
 * It maintains a map of cells, each identified by a {@code Position}, and a list of valid movement directions.
 * Subclasses are expected to implement specific rules for validating moves and managing the board state.
 * </p>
 */
public abstract class AbstractBoard {

    /** A map of all the cells on the board, where the key is the position of the cell. */
    protected Map<Position, Cell> cells = new HashMap<Position, Cell>();

    /** A list of valid movement directions from any given position. */
    protected List<Position> movements = new ArrayList<>();

    /**
     * Validates whether a move is legal from a given starting position to a destination position.
     * <p>
     * This method is abstract and must be implemented in subclasses to define specific movement rules for the game.
     * </p>
     *
     * @param player the player making the move
     * @param start the starting position of the move
     * @param destination the target position of the move
     * @return {@code true} if the move is valid according to the board's rules, {@code false} otherwise
     */
    public abstract boolean isValidMove(AbstractPlayer player, Position start, Position destination);
}

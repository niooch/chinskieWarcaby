package com.jkpr.chinesecheckers;

/**
 * Represents a game piece in Chinese checkers.
 * <p>
 * Each {@code Piece} is owned by a player, represented by the {@code AbstractPlayer} class.
 * This class provides a method to retrieve the owner of the piece.
 * </p>
 */
public class Piece {

    /** The player who owns this piece. */
    private AbstractPlayer owner;

    /**
     * Returns the owner of this piece.
     * <p>
     * The owner is an instance of {@code AbstractPlayer}, which can be used to
     * identify which player controls the piece in the game.
     * </p>
     *
     * @return the owner of this piece
     */
    public AbstractPlayer getOwner() {
        return owner;
    }
}

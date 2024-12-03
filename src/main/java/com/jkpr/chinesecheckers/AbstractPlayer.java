package com.jkpr.chinesecheckers;

import java.util.List;
import java.util.Objects;

/**
 * Represents a player in the game.
 * <p>
 * The {@code AbstractPlayer} class serves as a base class for creating players in the game.
 * It stores basic player attributes such as their name, ID, and a list of pieces they own.
 * The class also includes methods for equality comparison based on player ID.
 * </p>
 */
public abstract class AbstractPlayer {

    /** The name of the player. */
    private String name;

    /** The unique identifier for the player. */
    private int id;
    //TODO tutaj piece jest nawet spoko ale trzebaby było ogarnąć to tak żeby piece miało dokładnie
    // te same obiekty position i piece co gdzie indziej - generalnie jeszcze do przemyślenia
    // wtedy trzebabyłoby je trworzyć już przy inicjacji Game i przekazywać te instancje
    /** The list of pieces owned by the player. */
    private List<Piece> pieceList;

    /**
     * Checks whether this player is equal to another object.
     * <p>
     * Two players are considered equal if they have the same ID.
     * </p>
     *
     * @param o the object to compare with this player
     * @return {@code true} if the players are the same; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPlayer player = (AbstractPlayer) o;
        return id == player.id;
    }

    /**
     * Returns a hash code for this player based on their ID.
     * <p>
     * This ensures that players with the same ID have the same hash code, which is used in collections like {@code HashMap}.
     * </p>
     *
     * @return the hash code for this player
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


package com.jkpr.chinesecheckers;

/**
 * The {@code GameBuilder} interface defines the blueprint for constructing a game of Chinese checkers.
 * <p>
 * Classes that implement this interface should provide concrete implementations for setting up the board,
 * configuring the players, and returning the fully constructed {@code Game} object.
 * </p>
 */
public interface GameBuilder {

    /**
     * Sets up the board for the game.
     * <p>
     * This method is responsible for initializing and configuring the game board.
     * It should define how the board is arranged, including any starting positions or special settings.
     * </p>
     */
    void setBoard();

    /**
     * Sets up the players for the game.
     * <p>
     * This method should configure the players, including initializing player objects, assigning roles,
     * and determining player order or teams.
     * </p>
     */
    void setPlayers();

    /**
     * Returns the fully constructed game.
     * <p>
     * After the board and players have been set up, this method should return a {@code Game} object
     * that is ready to be played.
     * </p>
     *
     * @return the constructed {@code Game} object
     */
    Game getGame();
}

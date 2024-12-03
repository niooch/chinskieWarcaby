package com.jkpr.chinesecheckers;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board for Chinese checkers.
 * <p>
 * The {@code CCBoard} class extends {@code AbstractBoard} and defines the layout and movement rules
 * for the Chinese checkers board. It includes the logic for setting up the board with cells and defining
 * valid movements for players' pieces.
 * </p>
 */
public class CCBoard extends AbstractBoard {

    /**
     * Constructs a {@code CCBoard} with the appropriate layout and initial state.
     * <p>
     * This constructor creates the board by populating cells based on predefined movement rules and
     * board coordinates. It also assigns owners to each cell according to the rules of the game.
     * </p>
     */
    CCBoard() {
        // Movement possibilities
        movements.add(new Position(-1, 0));
        movements.add(new Position(1, 0));
        movements.add(new Position(0, -1));
        movements.add(new Position(0, 1));
        movements.add(new Position(1, 1));
        movements.add(new Position(-1, -1));

        // Board creation
        int cellNumber = 13;
        for (int y = -4; y <= 8; y++) {
            int x = -4;
            for (int k = 0; k < cellNumber; k++) {
                Position pos = new Position(x, y);
                if (!cells.containsKey(pos)) {
                    Cell cell = new Cell(pos, getOwners(x, y));
                    cells.put(pos, cell);
                }
                x++;
            }
            cellNumber--;
        }

        cellNumber = 13;
        for (int y = 4; y >= -8; y--) {
            int x = 4;
            for (int k = 0; k < cellNumber; k++) {
                Position pos = new Position(x, y);
                if (!cells.containsKey(pos)) {
                    Cell cell = new Cell(pos, getOwners(x, y));
                    cells.put(pos, cell);
                }
                x--;
            }
            cellNumber--;
        }
    }

    /**
     * Returns a list of players who own a particular cell based on the coordinates.
     * <p>
     * This method assigns owners to cells based on their (x, y) coordinates according to the rules of the game.
     * </p>
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return the list of {@code AbstractPlayer}s who own the cell at the given coordinates
     */
    //TODO trzeba jakoś wytrzasnąć instancje graczy przy tworzeniu
    // planszy żeby można było ustawić właścicieli pól. wtedy np po id wyciągałoby się instancje gracza
    // z hash mapy czy coś

    //TODO tak btw to żeby tu można było użyć List.of() to trzeba chyba dopisać wersję javy do pom.xml
    // a wtedy ładniej by to wyglądało (daje mi błąd że List.of jest od javy 9)
    private List<AbstractPlayer> getOwners(int x, int y) {
        if (y < -4 || y > 4) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(3);
            list.add(6);
            return list;
        }
        if (x < -4 || x > 4) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(4);
            list.add(1);
            return list;
        }
        if (x + y <= -5 || x + y >= 5) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(2);
            list.add(5);
            return list;
        } else {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            list.add(5);
            list.add(6);
            return list;
        }
    }

    /**
     * Validates if a move is legal for a given player.
     * <p>
     * This method checks if the starting position contains the player's piece and if the destination
     * is a valid move for that player, considering movement rules and cell ownership.
     * </p>
     *
     * @param player the player making the move
     * @param start the starting position of the move
     * @param destination the target position of the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    @Override
    public boolean isValidMove(AbstractPlayer player, Position start, Position destination) {
        if (cells.containsKey(start) && cells.get(start).checkPlayer(player)) {
            List<Position> possibilities = new ArrayList<>();
            findPossibilities(possibilities, player, start);
            return possibilities.contains(destination);
        }
        return false;
    }

    /**
     * Recursively finds all possible valid moves from a given starting position.
     * <p>
     * This method explores all legal moves from the start position by recursively checking all potential
     * moves in the directions defined by the {@code movements} list. It adds valid moves to the
     * {@code alreadyVisited} list.
     * </p>
     *
     * @param alreadyVisited the list of positions that have already been visited
     * @param player the player for whom the moves are being checked
     * @param start the starting position of the move
     */
    private void findPossibilities(List<Position> alreadyVisited, AbstractPlayer player, Position start) {
        for (Position move : movements) {
            Position potentialMove = new Position(start, move);
            if (isMoveLegal(potentialMove, player)) {
                alreadyVisited.add(new Position(start, move));
                potentialMove = new Position(potentialMove, move);
            } else if (!alreadyVisited.contains(potentialMove) && isMoveLegal(potentialMove, player)) {
                alreadyVisited.add(potentialMove);
                findPossibilities(alreadyVisited, player, potentialMove);
            }
        }
    }

    /**
     * Checks if a move to a specific position is legal for a given player.
     * <p>
     * This method checks if the destination position exists on the board, is empty, and is owned by the player.
     * </p>
     *
     * @param position the destination position
     * @param player the player making the move
     * @return {@code true} if the move is legal, {@code false} otherwise
     */
    private boolean isMoveLegal(Position position, AbstractPlayer player) {
        return cells.containsKey(position)
                && cells.get(position).isEmpty()
                && cells.get(position).getOwners().contains(player);
    }
}


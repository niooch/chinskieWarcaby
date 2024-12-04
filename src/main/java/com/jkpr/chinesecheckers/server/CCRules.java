package com.jkpr.chinesecheckers.server;

import java.util.ArrayList;
import java.util.List;

public class CCRules extends AbstractRules{
    @Override
    public boolean isValidMove(AbstractBoard board,Player player, Position start, Position destination) {
        if (board.getCells().containsKey(start) && board.getCells().get(start).checkPlayer(player)) {
            List<Position> possibilities = new ArrayList<>();
            findPossibilities(board,possibilities, player, start);
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
    private void findPossibilities(AbstractBoard board,List<Position> alreadyVisited, Player player, Position start) {
        for (Position move : board.getMovements()) {
            Position potentialMove = new Position(start, move);
            if (isMoveLegal(board,potentialMove, player)) {
                alreadyVisited.add(new Position(start, move));
                potentialMove = new Position(potentialMove, move);
            } else if (!alreadyVisited.contains(potentialMove) && isMoveLegal(board,potentialMove, player)) {
                alreadyVisited.add(potentialMove);
                findPossibilities(board,alreadyVisited, player, potentialMove);
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
    private boolean isMoveLegal(AbstractBoard board,Position position, Player player) {
        return board.getCells().containsKey(position)
                && board.getCells().get(position).isEmpty()
                && board.getCells().get(position).getOwners().contains(player);
    }
}

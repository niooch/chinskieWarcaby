package com.jkpr.chinesecheckers.server;

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
    private Player[] playerDistribution=new Player[6];
    /**
     * Constructs a {@code CCBoard} with the appropriate layout and initial state.
     * <p>
     * This constructor creates the board by populating cells based on predefined movement rules and
     * board coordinates. It also assigns owners to each cell according to the rules of the game.
     * </p>
     */
    public CCBoard(int count) {
        //setting players
        for(int i=0;i<count;i++)
            players.add(new Player(i));
        //distribution player array
        for(double i=0;i<6;i++)
        {
            double value=i*(double)count/6;
            if(Math.floor(value)==value)
            {
                playerDistribution[(int)i]=players.get((int)value);
            }
            else
            {
                playerDistribution[(int)i]=null;
            }
        }
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
                    cell.setPiece(getPiece(x,y));
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
    private List<Player> getOwners(int x, int y) {
        List<Player> list;
        if (y < -4 || y > 4) {
            list=List.of(playerDistribution[0],playerDistribution[3]);
        }
        else if (x < -4 || x > 4) {
            list=List.of(playerDistribution[1],playerDistribution[4]);
        }
        else if (x + y <= -5 || x + y >= 5) {
            list=List.of(playerDistribution[2],playerDistribution[5]);
        } else {
            list=List.of(playerDistribution[0],playerDistribution[3],
                    playerDistribution[1],playerDistribution[4],
                    playerDistribution[2],playerDistribution[5]);
        }
        return list;
    }

    private Piece getPiece(int x, int y) {
        if (y < -4) {
            Player player=playerDistribution[0];
            if(player==null)
                return null;
            else
                return new Piece(player);
        }
        else if (y > 4) {
            Player player=playerDistribution[3];
            if(player==null)
                return null;
            else
                return new Piece(player);
        }
        else if (x < -4) {
            Player player=playerDistribution[1];
            if(player==null)
                return null;
            else
                return new Piece(player);
        }
        else if (x > 4) {
            Player player=playerDistribution[4];
            if(player==null)
                return null;
            else
                return new Piece(player);
        }
        else if ( x + y >= 5) {
            Player player=playerDistribution[2];
            if(player==null)
                return null;
            else
                return new Piece(player);
        }
        else if (x + y <= -5) {
            Player player=playerDistribution[5];
            if(player==null)
                return null;
            else
                return new Piece(player);
        }
        else {
            return null;
        }
    }

    @Override
    public String toString() {
        String s="";
        for(int i=-8;i<=8;i++)
        {
            for(int j=-8;j<=8;j++)
            {
                if(cells.get(new Position(i,j))==null)
                {
                    s.concat(" ");
                }
                else
                {
                    s.concat(cells.get(new Position(i,j)).toString());
                }
            }
        }
        return s;
    }

    @Override
    public void makeMove(Position start, Position end) {
        cells.get(end).setPiece(cells.get(start).getPiece());
        cells.get(start).setPiece(null);
    }
}


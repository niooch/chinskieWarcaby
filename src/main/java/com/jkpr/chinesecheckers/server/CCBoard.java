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
    private List<Player> getOwners(int x, int y) {
        ArrayList<Player> list = new ArrayList<>();
        if (y < -4 || y > 4) {
            list.add(new Player(0));
            list.add(new Player(1));
            return list;
        }
        if (x < -4 || x > 4) {
            list.add(new Player(2));
            list.add(new Player(3));
            return list;
        }
        if (x + y <= -5 || x + y >= 5) {
            list.add(new Player(4));
            list.add(new Player(5));
            return list;
        } else {
            list.add(new Player(3));
            list.add(new Player(0));
            list.add(new Player(4));
            list.add(new Player(1));
            list.add(new Player(2));
            list.add(new Player(5));
            return list;
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
}


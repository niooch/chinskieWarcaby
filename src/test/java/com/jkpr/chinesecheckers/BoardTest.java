package com.jkpr.chinesecheckers;

import com.jkpr.chinesecheckers.server.gamelogic.*;
import com.jkpr.chinesecheckers.server.gamelogic.boards.AbstractBoard;
import com.jkpr.chinesecheckers.server.gamelogic.boards.CCBoard;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BoardTest extends TestCase {
    public static Test suite()
    {
        return new TestSuite( BoardTest.class );
    }
    public void testGen(){
        //2 players
        AbstractBoard board=new CCBoard(2);
        Player testOwner=board.getCells().get(new Position(3,-6)).getPiece().getOwner();
        Player expected=board.getPlayer(0);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(-3,6)).getPiece().getOwner();
        expected=board.getPlayer(1);
        assertEquals(expected,testOwner);

        //3 players
        board=new CCBoard(3);
        testOwner=board.getCells().get(new Position(3,-6)).getPiece().getOwner();
        expected=board.getPlayer(0);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(3,3)).getPiece().getOwner();
        expected=board.getPlayer(2);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(-6,3)).getPiece().getOwner();
        expected=board.getPlayer(1);
        assertEquals(expected,testOwner);

        //4 players
        board=new CCBoard(4);
        testOwner=board.getCells().get(new Position(3,-6)).getPiece().getOwner();
        expected=board.getPlayer(0);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(-3,6)).getPiece().getOwner();
        expected=board.getPlayer(3);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(6,-3)).getPiece().getOwner();
        expected=board.getPlayer(2);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(-6,3)).getPiece().getOwner();
        expected=board.getPlayer(1);
        assertEquals(expected,testOwner);

        //6 players
        board=new CCBoard(6);
        testOwner=board.getCells().get(new Position(3,3)).getPiece().getOwner();
        expected=board.getPlayer(2);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(-3,-3)).getPiece().getOwner();
        expected=board.getPlayer(5);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(-3,6)).getPiece().getOwner();
        expected=board.getPlayer(3);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(3,-6)).getPiece().getOwner();
        expected=board.getPlayer(0);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(6,-3)).getPiece().getOwner();
        expected=board.getPlayer(1);
        assertEquals(expected,testOwner);

        testOwner=board.getCells().get(new Position(-6,3)).getPiece().getOwner();
        expected=board.getPlayer(4);
        assertEquals(expected,testOwner);

    }
    public void testMoves()
    {
        AbstractBoard board=new CCBoard(2);
        AbstractRules rules=new CCRules();
        //valid moves
        assertTrue(rules.isValidMove(board, board.getPlayer(0), new Position(3,-5), new Position(3,-4)));
        board.makeMove(new Position(3,-5), new Position(3,-4));
        assertFalse(rules.isValidMove(board, board.getPlayer(0), new Position(3,-5), new Position(3,-4)));
        assertTrue(rules.isValidMove(board, board.getPlayer(0), new Position(4,-5), new Position(2,-3)));
    }
}

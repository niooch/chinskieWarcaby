package com.jkpr.chinesecheckers.server.message;

import com.jkpr.chinesecheckers.server.Move;

public class MoveMessage extends Message{
    private Move move;
    public MoveMessage(Move move){
        super(MessageType.MOVE);
        this.move = move;
    }
    public MoveMessage(int q1, int r1, int q2, int r2){
        super(MessageType.MOVE);
        this.move = new Move(q1, r1, q2, r2);
    }

    public Move getMove(){
        return move;
    }
}

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
    public MoveMessage(String message)
    {
        super(MessageType.MOVE);
        String[] blocks=message.split(" ");
        String[] start=blocks[0].split(",");
        String[] end=blocks[1].split(",");

        int startX=Integer.parseInt(start[0]);
        int startY=Integer.parseInt(start[1]);
        int endX=Integer.parseInt(end[0]);
        int endY=Integer.parseInt(end[1]);

        move=new Move(startX,startY,endX,endY);
    }
    public Move getMove(){
        return move;
    }
}

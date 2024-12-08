package com.jkpr.chinesecheckers.server.message;

import com.jkpr.chinesecheckers.server.Move;

public class MoveMessage extends Message{
    //dla serwera
    private final Move move;
    //dla klienta
    private final int q1, r1, q2, r2;
    public MoveMessage(Move move){
        super(MessageType.MOVE);
        this.move = move;
        this.q1 = move.getStart().getX();
        this.r1 = move.getStart().getY();
        this.q2 = move.getEnd().getX();
        this.r2 = move.getEnd().getY();
    }
    public MoveMessage(int q1, int r1, int q2, int r2){
        super(MessageType.MOVE);
        this.q1 = q1;
        this.r1 = r1;
        this.q2 = q2;
        this.r2 = r2;
        this.move = new Move(q1, r1, q2, r2);
    }
    public int getQ1(){
        return q1;
    }
    public int getR1(){
        return r1;
    }
    public int getQ2(){
        return q2;
    }
    public int getR2(){
        return r2;
    }
    @Override
    public String serialize(){
        return getType().name()+ " " + q1 + "," + r1 + " " + q2 + "," + r2;
    }
    public static MoveMessage fromContent(String content){
        String[] parts = content.split(" ");
        String[] start = parts[0].split(",");
        String[] end = parts[1].split(",");
        return new MoveMessage(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(end[0]), Integer.parseInt(end[1]));
    }
    public Move getMove(){
        return move;
    }
}

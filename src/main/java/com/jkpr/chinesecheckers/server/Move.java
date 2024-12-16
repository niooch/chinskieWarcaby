package com.jkpr.chinesecheckers.server;

public class Move {
    private Position start;
    private Position end;

    public Move(int xStart,int yStart,int xEnd,int yEnd)
    {
        start=new Position(xStart,yStart);
        end=new Position(xEnd,yEnd);
    }

    public Position getEnd() {
        return end;
    }

    public Position getStart() {
        return start;
    }
    @Override
    public String toString()
    {
        return start.toString()+" "+end.toString();
    }
}

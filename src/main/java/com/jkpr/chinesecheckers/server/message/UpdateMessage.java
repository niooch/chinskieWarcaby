package com.jkpr.chinesecheckers.server.message;

import com.jkpr.chinesecheckers.server.Move;

public class UpdateMessage extends Message {
    private int playerId;
    private int nextPlayer;
    private Move move;

    public UpdateMessage(String string)
    {
        super(MessageType.UPDATE);
        String[] blocks=string.split(" ");
        playerId=Integer.parseInt(blocks[1]);
        nextPlayer=Integer.parseInt(blocks[2]);
        int startX=Integer.parseInt(blocks[3]);
        int startY=Integer.parseInt(blocks[4]);
        int endX=Integer.parseInt(blocks[5]);
        int endY=Integer.parseInt(blocks[6]);
        move=new Move(startX,startY,endX,endY);
    }
}

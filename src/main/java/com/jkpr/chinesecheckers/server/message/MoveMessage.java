package com.jkpr.chinesecheckers.server.message;

import com.jkpr.chinesecheckers.server.Move;

public class MoveMessage implements Message{
    private Move move;
    public MoveMessage(Move move){
        this.move = move;
    }

    public Move getMove(){
        return move;
    }

    @Override
    public MessageType getType() {
        return MessageType.MOVE;
    }
}

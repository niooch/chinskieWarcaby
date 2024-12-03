package com.jkpr.chinesecheckers.server.message;

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

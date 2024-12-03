package com.jkpr.chinesecheckers.server.message;

public class UpdateMessage implements Message {
    private Board board;

    public UpdateMessage(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public MessageType getType() {
        return MessageType.UPDATE;
    }
}

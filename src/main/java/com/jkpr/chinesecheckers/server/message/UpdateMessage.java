package com.jkpr.chinesecheckers.server.message;

import com.jkpr.chinesecheckers.server.AbstractBoard;

public class UpdateMessage implements Message {
    private AbstractBoard board;

    public UpdateMessage(AbstractBoard board) {
        this.board = board;
    }

    public AbstractBoard getBoard() {
        return board;
    }

    @Override
    public MessageType getType() {
        return MessageType.UPDATE;
    }
}

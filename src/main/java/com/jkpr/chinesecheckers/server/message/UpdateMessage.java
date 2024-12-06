package com.jkpr.chinesecheckers.server.message;

import com.jkpr.chinesecheckers.server.AbstractBoard;

public class UpdateMessage extends Message {
    private AbstractBoard board;

    public UpdateMessage(AbstractBoard board) {
        super(MessageType.UPDATE);
        this.board = board;
    }

    public AbstractBoard getBoard() {
        return board;
    }

}

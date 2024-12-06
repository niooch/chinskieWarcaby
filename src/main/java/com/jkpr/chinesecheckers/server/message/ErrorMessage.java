package com.jkpr.chinesecheckers.server.message;

public class ErrorMessage extends Message {
    private String error;

    public ErrorMessage(String error) {
        super(MessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}

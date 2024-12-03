package com.jkpr.chinesecheckers.server.message;

public class ErrorMessage implements Message {
    private String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public MessageType getType() {
        return MessageType.ERROR;
    }
}

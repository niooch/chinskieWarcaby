package com.jkpr.chinesecheckers.server.message;

public abstract class Message {
    private MessageType type;
    public Message(MessageType type) {
        this.type = type;
    }
    public MessageType getType() {
        return type;
    }
}

package com.jkpr.chinesecheckers.server.message;

public class StartMessage extends Message {
    private String playerId;
    private String message;

    public StartMessage(String playerId, String message) {
        super(MessageType.START_GAME);
        this.playerId = playerId;
        this.message = message;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getMessage() {
        return message;
    }
}

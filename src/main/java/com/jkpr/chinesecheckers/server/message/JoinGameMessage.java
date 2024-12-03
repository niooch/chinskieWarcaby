package com.jkpr.chinesecheckers.server.message;

public class JoinGameMessage implements Message {
    private int playerCount;

    public JoinGameMessage(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    @Override
    public MessageType getType() {
        return MessageType.JOIN;
    }
}

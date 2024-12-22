package com.jkpr.chinesecheckers.server.gamelogic;

public abstract class AbstractRules {
    abstract boolean isValidMove(AbstractBoard board,Player player,Position start,Position destination);
}

package com.jkpr.chinesecheckers.server;

public abstract class AbstractRules {
    abstract boolean isValidMove(AbstractBoard board,Player player,Position start,Position destination);
}

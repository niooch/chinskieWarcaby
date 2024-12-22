package com.jkpr.chinesecheckers.server.states;

public interface PlayerBehavior {
    PlayerState getState();
    PlayerBehavior setWin();
    PlayerBehavior setActive();
    PlayerBehavior setWait();

}

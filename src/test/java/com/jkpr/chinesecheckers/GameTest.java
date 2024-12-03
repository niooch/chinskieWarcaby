package com.jkpr.chinesecheckers;


import junit.framework.TestCase;

public class GameTest extends TestCase {
    public void generalTest()
    {
        Game game= Director.createGame(new CCBuilder());
    }
}

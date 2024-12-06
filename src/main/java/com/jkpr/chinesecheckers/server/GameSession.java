package com.jkpr.chinesecheckers.server;

import java.util.*;

public class GameSession {
    private int playerCount;
    private Server server;
    private List<ClientHandler> players = new ArrayList<>();
    private HashMap<ClientHandler,Player> clientHandlerPlayerHashMap;
    private Game game;
    public GameSession(int playerCount,Server server)
    {
        clientHandlerPlayerHashMap=new HashMap<>();

        this.playerCount=playerCount;
        this.server=server;

        game=Director.createGame(new CCBuilder(),playerCount);
    }
    public void addPlayer(ClientHandler clientHandler)
    {
        clientHandlerPlayerHashMap.put(clientHandler,game.join());
    }
    public void processMove(Move move,ClientHandler clientHandler){
        game.nextMove(move,clientHandlerPlayerHashMap.get(clientHandler));
    }
}

package com.jkpr.chinesecheckers.server;

import java.util.*;

public class GameSession {
    private Server server;
    private List<ClientHandler> players = new ArrayList<>();
    private HashMap<ClientHandler,Player> clientHandlerPlayerHashMap;
    private Game game;
    public GameSession(ClientHandler[] players,Server server){
        clientHandlerPlayerHashMap=new HashMap<>();

        this.server=server;
        for(ClientHandler clientHandler:players)
        {
            addPlayer(clientHandler);
        }
        int playerCount=players.length;

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

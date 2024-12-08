package com.jkpr.chinesecheckers.server;

import java.util.*;
import com.jkpr.chinesecheckers.server.message.*;

public class GameSession {
    private Server server;
    private List<ClientHandler> players = new ArrayList<>();
    private HashMap<ClientHandler,Player> clientHandlerPlayerHashMap;
    private Game game;
    public GameSession(ClientHandler[] players,Server server){
        //game=Director.createGame(new CCBuilder(),players.length);
        clientHandlerPlayerHashMap=new HashMap<>();
        this.players=Arrays.asList(players);

        this.server=server;
        for(ClientHandler clientHandler:players)
        {
            addPlayer(clientHandler);
        }

    }
    public void addPlayer(ClientHandler clientHandler)
    {
        //clientHandlerPlayerHashMap.put(clientHandler,game.join());
    }
    public void processMove(Move move,ClientHandler clientHandler){
        game.nextMove(move,clientHandlerPlayerHashMap.get(clientHandler));
    }
    public void brodcastMessage (MoveMessage move, ClientHandler clientHandler){
        for(ClientHandler player:players){
            System.out.println("Sending message to "+player.getPlayerId());
            player.sendMessage(move);
        }
    }

}

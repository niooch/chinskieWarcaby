package com.jkpr.chinesecheckers.server;

import java.io.*;
import java.net.Socket;
import com.jkpr.chinesecheckers.server.message.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private GameSession gameSession;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        try{
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("blad iniclaizacji strumieni");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            while(true){
                Message msg = (Message) in.readObject();
                handleMessage(msg);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("blad odczytu wiadomosci");
            e.printStackTrace();
        } finally {
            cleanUp();
        }
    }

    private void handleMessage(Message msg) {
        switch (msg.getType()) {
            case JOIN:
                handleJoinGame((JoinGameMessage) msg);
                break;
            case MOVE:
                handleMove((MoveMessage) msg);
                break;
            // tutaj ida inne typy wiadomosci
            default:
                System.err.println("nieznany typ wiadomosci");
        }
    }

    private void handleJoinGame(JoinGameMessage msg) {
        int playerCount = msg.getPlayerCount();
        gameSession = server.createGameSession(playerCount);
        gameSession.addPlayer(this);
    }

    private void handleMove(MoveMessage msg) {
        if(gameSession != null){
            gameSession.processMove(msg.getMove(), this);
        } else {
            sendMessage(new ErrorMessage("nie jestes w zadnej grze!"));
        }
    }

    private void sendMessage(Message msg) {
        try{
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            System.err.println("blad wysylania wiadomosci do klienta");
            e.printStackTrace();
        }
    }

    private void cleanUp() {
        server.removeClientHandler(this);
        try{
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("blad zamykania gniazda klienta");
            e.printStackTrace();
        }
    }
}

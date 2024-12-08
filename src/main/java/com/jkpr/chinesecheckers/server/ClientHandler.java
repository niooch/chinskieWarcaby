package com.jkpr.chinesecheckers.server;

import java.io.*;
import java.net.Socket;
import com.jkpr.chinesecheckers.server.message.*;
import java.util.UUID;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private GameSession gameSession;
    private String playerId;
    private boolean isInGame;
    private ClientQueue clientQueue;

    public ClientHandler(Socket clientSocket, Server server, ClientQueue clientQueue) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.clientQueue = clientQueue;
        this.isInGame = false;
        this.gameSession = gameSession;
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
            clientQueue.addClient(this);
            while(true){
                String linia = (String) in.readObject();
                Message message = Message.fromString(linia);
                if(message.getType() == MessageType.MOVE){
                    handleMoveMessage((MoveMessage) message);
                }
                else{
                    System.out.println("nieznany typ wiadomosci");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("blad odczytu wiadomosci");
            e.printStackTrace();
        } finally {
            cleanUp();
        }
    }
    private void handleMoveMessage(MoveMessage message) {
        System.out.println("odebrano wiadomosc MOVE od " + playerId + ": " + message.serialize());
        gameSession.brodcastMessage(message, this);
    }

    public void sendMessage(Message message) {
        try{
            out.writeObject(message.serialize());
            out.flush();
        } catch (IOException e) {
            System.err.println("blad wysylania wiadomosci");
            e.printStackTrace();
        }
    }

    public String getPlayerId() {
        if(playerId == null){
            playerId = UUID.randomUUID().toString();
        }
        return playerId;
    }

    public void assignGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
        this.isInGame = true;
    }
    public void closeConnection() {
        try{
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("blad zamykania gniazda klienta");
            e.printStackTrace();
        }
    }
    private void cleanUp() {
        try{
            clientSocket.close();
            clientQueue.removeClient(this);

        } catch (IOException e) {
            System.err.println("blad zamykania gniazda klienta");
            e.printStackTrace();
        }
    }
}

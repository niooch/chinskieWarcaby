package com.jkpr.chinesecheckers.server;

import java.io.*;
import java.net.Socket;
import com.jkpr.chinesecheckers.server.message.*;
import java.util.UUID;
import com.google.gson.Gson;

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
            System.out.println("klient dodany do kolejki. Id: " + playerId);
            while(true){
                //pobranie wiadomosci od serwera
                String jsonMessage = (String) in.readObject();
                Message msg =new Gson().fromJson(jsonMessage, Message.class);
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
            case MOVE:
                handleMove((MoveMessage) msg);
                break;
                //tutaj obsluga innych typow wiadomosci
            default:
                System.err.println("nieznany typ wiadomosci");
        }
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
            String jsonMessage = new Gson().toJson(msg);
            out.writeObject(jsonMessage);
            out.flush();
        } catch (IOException e) {
            System.err.println("blad wysylania wiadomosci do klienta");
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

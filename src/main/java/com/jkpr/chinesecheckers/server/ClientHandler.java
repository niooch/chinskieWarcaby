package com.jkpr.chinesecheckers.server;

import java.io.*;
import java.net.Socket;
import com.jkpr.chinesecheckers.server.message.*;

import java.util.Scanner;
import java.util.UUID;
import com.google.gson.Gson;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private Scanner in;
    private GameSession gameSession;
    private String playerId;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try{
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            out.println("WAIT");
            in = new Scanner(clientSocket.getInputStream());

            //TODO w zasadzie to nie wiem jak to zrobić żeby wszyskie wątki mogły swobodnie się słuchać
            //główny while serwera-według mnie przynajmniej
            while(true){
                try {
                    System.out.println("słucham");
                    //pobranie wiadomosci od klienta
                    String message = in.nextLine();
                    System.out.println(message);
                    //TODO tutaj będzie włączana logika gry i przetwarzany ruch, na razie wiadomość jest po prostu zwracana
                    Message msg = new MoveMessage(message);
                    //handleMessage(msg);
                    out.println(message + "returned");
                }catch(Exception e){}
            }
        } catch (IOException e) {
            System.err.println("blad iniclaizacji strumieni");
            e.printStackTrace();
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
        String jsonMessage = new Gson().toJson(msg);
        out.println(jsonMessage);
        out.flush();
    }

    public String getPlayerId() {
        if(playerId == null){
            playerId = UUID.randomUUID().toString();
        }
        return playerId;
    }

    public void closeConnection() {
        try{
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("blad zamykania gniazda klienta");
            e.printStackTrace();
        }
    }
}

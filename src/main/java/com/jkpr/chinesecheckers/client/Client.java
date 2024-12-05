package com.jkpr.chinesecheckers.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import com.jkpr.chinesecheckers.server.AbstractBoard;
import com.jkpr.chinesecheckers.server.Move;
import com.jkpr.chinesecheckers.server.message.*;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Scanner scanner;
    private Boolean running;

    public static void main(String[] args){
        Client client = new Client();
        client.start();
    }
    public void start() {
        try {
            connectToServer();
            new Thread(this::recieveMessages).start();
            handleUserInput();
        } catch (IOException e) {
            System.err.println("blad polacznia z serwerem: " + e.getMessage());
        }
    }

    private void connectToServer() throws IOException {
        socket = new Socket("localhost", 12345);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        scanner = new Scanner(System.in);
        System.out.println("polaczono z serwerem");
        running = true;
    }

    private void handleUserInput () {
        while (running) {
            System.out.println("wpisz ruch (q1,r1 q2,r2) lub 'exit' aby zakonczyc");
            String input = scanner.nextLine();
            if (input == "exit") {
                System.out.println("koncze dzialanie");
                running = false;
                closeConnection();
                break;
            }
            Move move = parseMoveInput(input);
            if (move != null) {
                sendMessage(new MoveMessage(move));
            } else {
                System.out.println("niepoprawny ruch");
            }
        }
    }

    private Move parseMoveInput (String input){
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            return null;
        }
        String[] from = parts[0].split(",");
        String[] to = parts[1].split(",");
        if (from.length != 2 || to.length != 2) {
            return null;
        }
        try {
            int fromQ = Integer.parseInt(from[0]);
            int fromR = Integer.parseInt(from[1]);
            int toQ = Integer.parseInt(to[0]);
            int toR = Integer.parseInt(to[1]);
            return new Move(fromQ, fromR, toQ, toR);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void sendMessage (Message message){
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println("blad wysylania wiadomosci: " + e.getMessage());
            running = false;
            closeConnection();
        }
    }

    private void recieveMessages() {
        while (running) {
            try {
                Message message = (Message) in.readObject();
                processMessage(message);
            } catch (IOException e) {
                System.err.println("polaczenie z serwerem zerwane: " + e.getMessage());
                running = false;
                closeConnection();
            } catch (ClassNotFoundException e) {
                System.err.println("nieznany typ wiadomosci: " + e.getMessage());
            }
        }
    }

    private void processMessage (Message message){
        switch (message.getType()) {
            case START_GAME:
                System.out.println("gra sie rozpoczyna");
                break;
            case UPDATE:
                UpdateMessage update = (UpdateMessage) message;
                displayBoard(update.getBoard());
                break;
            case ERROR:
                ErrorMessage error = (ErrorMessage) message;
                System.out.println("blad: " + error.getError());
                break;
            default:
                System.out.println("nieznany typ wiadomosci" + message.getType());
                break;
        }
    }

    private void displayBoard (AbstractBoard board){
        System.out.println(board.toString());
    }

    private void closeConnection () {
        try {
            if (socket != null) {
                socket.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            System.err.println("blad zamykania polaczenia: " + e.getMessage());
        }
    }
}

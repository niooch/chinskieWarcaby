package com.jkpr.chinesecheckers.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

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
            scanner = new Scanner(System.in); // do czytanie wpisu z konsoli klienta
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
        System.out.println("polaczono z serwerem");
        running = true;
    }

    private void handleUserInput () {
        while (running) {
            System.out.println("wpisz ruch (q1,r1 q2,r2)");
            String input = scanner.nextLine().trim();
            MoveMessage message = MoveMessage.fromContent(input);
            try {
                out.writeObject(message.serialize());
                out.flush();
            } catch (IOException e) {
                System.err.println("blad wysylania ruchu: " + e.getMessage());
                running = false;
            } catch (NumberFormatException e) {
                System.out.println("niepoprawny format ruchu");
            }

        }
    }



    private void recieveMessages() {
        while (running) {
            try {
                String linia = (String) in.readObject();
                Message message = Message.fromString(linia);
                if(message.getType() == MessageType.UPDATE) {
                    System.out.println("Otrzymano aktualizacje planszy");
                    UpdateMessage update = (UpdateMessage) message;
                    System.out.println(update.getContent());
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("blad odczytu wiadomosci: " + e.getMessage());
                running = false;
            }

        }
    }

}

package com.jkpr.chinesecheckers.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;
import com.jkpr.chinesecheckers.server.message.*;

public class Client {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Scanner scanner;

    public Client(){
        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(),true);
            in = new Scanner(socket.getInputStream());
            scanner = new Scanner(System.in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        Client client = new Client();
        client.handleUserInput();
    }

    private void handleUserInput () {
        //TODO trzeba jakoś te wątki zsynchronizować
        //główny while klienta-według mnie przynajmniej
        while (true) {
            try {
                String message = in.nextLine();
                System.out.println(message);

                //TODO tutaj wiadomość zwrotna będzie przetwarzana
                //processMessage(message);

                System.out.println("wpisz ruch (q1,r1 q2,r2) lub 'exit' aby zakonczyc");
                String input = scanner.nextLine();

                out.println(input);
                System.out.println("wysyłam wiadomość");

                if (input.equals("exit")) {
                    System.out.println("koncze dzialanie");
                    closeConnection();
                    break;
                }
                String[] parts = input.split(" ");
                if (parts.length != 2) {
                    System.out.println("niepoprawny format ruchu");
                    continue;
                }
                String[] from = parts[0].split(",");
                String[] to = parts[1].split(",");
                if (from.length != 2 || to.length != 2) {
                    System.out.println("niepoprawny format ruchu");
                    continue;
                }
                try {
                    MoveMessage move = new MoveMessage(Integer.parseInt(from[0]), Integer.parseInt(from[1]), Integer.parseInt(to[0]), Integer.parseInt(to[1]));
                    String jsonMessage = move.toString();
                    out.println(jsonMessage);
                } catch (NumberFormatException e) {
                    System.out.println("niepoprawny format ruchu");
                }
            }catch (Exception e){}
        }
    }


    private void processMessage (String message){
        String[] blocks=message.split(" ");
        switch (blocks[0]) {
            case "START_GAME":
                System.out.println("gra sie rozpoczyna");
                break;
            case "UPDATE":
                UpdateMessage update = new UpdateMessage(message);
                break;
            case "ERROR":
                ErrorMessage error = new ErrorMessage(message);
                System.out.println("blad: " + error.getError());
                break;
            default:
                System.out.println("nieznany typ wiadomosci");
                break;
        }
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

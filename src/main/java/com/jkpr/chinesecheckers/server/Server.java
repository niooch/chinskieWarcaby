package com.jkpr.chinesecheckers.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import com.jkpr.chinesecheckers.server.message.*;

public class Server {
    private static final int PORT = 12345;          //port serwera
    private ServerSocket serverSocket;              //socket serwera
    private ExecutorService threadPool;             //pula watkow do odpalania watkow dla klientow
    private volatile boolean isRunning = true;             //flaga czy serwer dziala
    private ClientHandler[] players;
    private Scanner scanner;

    public void startServer() {
        try {
            //stworz serwer na porcie PORT
            serverSocket = new ServerSocket(PORT);
            threadPool = Executors.newCachedThreadPool();
            System.out.println("serwer wystartowal, zaczynam sluchac na " + PORT);

            //ile graczy?
            System.out.println("Podaj ilosc graczy: 2, 3, 4 lub 6");
            scanner = new Scanner(System.in);
            int numberOfPlayers = scanner.nextInt();
            if(numberOfPlayers <2 || numberOfPlayers > 6|| numberOfPlayers == 5){
                System.out.println("Niepoprawna liczba graczy");
                return;
            }
            System.out.println("czekam na " + numberOfPlayers + " graczy");
            players = new ClientHandler[numberOfPlayers];
            int connectedPlayers = 0;
            //czekaj na polaczenie sie odpwiedniej liczby graczy
            while(connectedPlayers < numberOfPlayers&& isRunning){
                Socket clientSocket = serverSocket.accept();
                System.out.println("akceptuje polaczenie od " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                System.out.println("dodaje gracza " +  handler.getPlayerId() + "do kolejki");
                players[connectedPlayers] = handler;
                threadPool.execute(handler);
                connectedPlayers++;
            }
            //odpal sesje gry
            System.out.println("wszyscy gracze dolaczyli, tworze gre");
            GameSession gameSession = new GameSession(players, this);
            for (ClientHandler handler : players) {
                //aby gracz mogl wysylac wiadomosci do innych
                handler.assignGameSession(gameSession);
            }
            while(true){
                //aby istnal watek serwera, potrzebny przechowywania instancji gameSession
            }
        } catch (IOException e) {
            System.err.println("blad serwera, nie moge wystartowac na porcie " + PORT);
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}

package com.jkpr.chinesecheckers.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;
    private ExecutorService threadPool;
    private List<GameSession> gameSessions;
    private ClientQueue clientQueue;
    private volatile boolean isRunning;
    private Scanner scanner;

    public Server(){
        threadPool = Executors.newCachedThreadPool();
        clientQueue = new ClientQueue();
        gameSessions = Collections.synchronizedList(new ArrayList<>());
        clientHandlers = Collections.synchronizedList(new ArrayList<>());
        isRunning = true;
        scanner = new Scanner(System.in);
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("serwer wystartowal, zaczynam sluchac na " + PORT);
            //Tworzenie gry <-- stary kod GameCreationManager
            System.out.println("Podaj ilosc graczy: 2, 3, 4 lub 6");
            int numberOfPlayers = scanner.nextInt();
            if(numberOfPlayers <2 || numberOfPlayers > 6|| numberOfPlayers == 5){
                System.out.println("Niepoprawna liczba graczy");
                shutdown();
            }
            System.out.println("czekam na " + numberOfPlayers + " graczy");
            //-------------------
            int connectedPlayers = 0;
            while(connectedPlayers < numberOfPlayers&& isRunning){
                Socket clientSocket = serverSocket.accept();
                System.out.println("akceptuje polaczenie od " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket, this, clientQueue);
                System.out.println("dodaje gracza " +  handler.getPlayerId() + "do kolejki");
                clientHandlers.add(handler);
                threadPool.execute(handler);
                connectedPlayers++;
            }
            ClientHandler[] players = new ClientHandler[numberOfPlayers];
            for(int i=0; i< numberOfPlayers; i++){
                players[i] = clientQueue.takeClient();
            }
            System.out.println("wszyscy gracze dolaczyli, tworze gre");
            GameSession gameSession = new GameSession(players, this);
            addGameSession(gameSession);

        } catch (IOException e) {
            System.err.println("blad serwera, nie moge wystartowac na porcie " + PORT);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }
    public synchronized void addGameSession(GameSession gameSession){
        gameSessions.add(gameSession);
    }
    public synchronized void removeClientHandler(ClientHandler clientHandler){
        clientHandlers.remove(clientHandler);
    }

    public void shutdown(){
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(ClientHandler handler : clientHandlers){
            handler.closeConnection();
        }
        threadPool.shutdownNow();
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}

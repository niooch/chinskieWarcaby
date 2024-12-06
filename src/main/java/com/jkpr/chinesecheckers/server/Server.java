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

    public Server(){
        threadPool = Executors.newCachedThreadPool();
        clientQueue = new ClientQueue();
        gameSessions = Collections.synchronizedList(new ArrayList<>());
        clientHandlers = Collections.synchronizedList(new ArrayList<>());
        isRunning = true;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("serwer wystartowal, zaczynam sluchac na " + PORT);
            GameCreationManager creationManager = new GameCreationManager(clientQueue, this);
            threadPool.execute(creationManager);
            while(isRunning){
                Socket clientSocket = serverSocket.accept();
                System.out.println("akceptuje polaczenie od " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket, this, clientQueue);
                clientHandlers.add(handler);
                threadPool.execute(handler);
            }
        } catch (IOException e) {
            System.err.println("blad serwera, nie moge wystartowac na porcie " + PORT);
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

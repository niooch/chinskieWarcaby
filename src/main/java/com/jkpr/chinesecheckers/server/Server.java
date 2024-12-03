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
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("serwer wystartowal, zaczynam sluchac na " + PORT);
            acceptConnections();
        } catch (IOException e) {
            System.err.println("blad serwera, nie moge wystartowac na porcie " + PORT);
            e.printStackTrace();
        }
    }
    private void acceptConnections(){
        while(true){
            try{
                Socket clientSocket = serverSocket.accept();
                System.out.println("akceptuje polaczenie od " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandlers.add(clientHandler);
                threadPool.execute(clientHandler);
            } catch (IOException e){
                System.err.println("blad serwera, nie moge zaakceptowac polaczenia");
                e.printStackTrace();
            }
        }
    }

    public synchronized void removeClientHandler(ClientHandler clientHandler){
        clientHandlers.remove(clientHandler);
    }

    public synchronized GameSession createGameSession(int playerCount){
        GameSession session = new GameSession(playerCount, this);
        gameSessions.add(session);
        return session;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}

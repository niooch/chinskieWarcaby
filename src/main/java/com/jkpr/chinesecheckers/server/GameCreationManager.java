package com.jkpr.chinesecheckers.server;

import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class GameCreationManager implements Runnable {
    private final ClientQueue clientQueue;
    private final Server server;
    private final Scanner scanner;

    public GameCreationManager(ClientQueue clientQueue, Server server) {
        this.clientQueue = clientQueue;
        this.server = server;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        System.out.println("game manager wsytartowal. czekam na specyfikacje gry");
        while (true) {
            try {
                System.out.println("wprowadz ilosc graczy: 2, 3, 4 lub 6, albo wpisz 'exit' aby zakonczyc");
                String input = scanner.nextLine();
                if (input.equals("exit")) {
                    System.out.println("koncze prace game managera");
                    server.shutdown();
                    break;
                }
                int playerCount = Integer.parseInt(input);
                if (playerCount < 2 || playerCount > 6) {
                    System.out.println("niepoprawna ilosc graczy");
                    continue;
                }

                System.out.println("czekam na "+playerCount+" graczy");
                ClientHandler[] players = new ClientHandler[playerCount];
                for (int i = 0; i < playerCount; i++) {
                    players[i] = clientQueue.takeClient();
                    System.out.println("gracz "+players[i].getPlayerId()+" dolaczyl do gry");
                }
                System.out.println("wszyscy gracze dolaczyli do gry");
                System.out.println("tworze gre");
                GameSession gameSession = new GameSession(players, server);
            } catch (NumberFormatException e) {
                System.out.println("niepoprawny format liczby graczy");
            } catch (InterruptedException e) {
                System.out.println("przerwano oczekiwanie na graczy (blad GM)");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

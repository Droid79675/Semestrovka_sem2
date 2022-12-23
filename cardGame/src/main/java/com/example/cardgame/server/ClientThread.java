package com.example.cardgame.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientThread implements Runnable {

    private final BufferedReader input;
    private final BufferedWriter output;
    private GameServer gameServer;

    public BufferedReader getInput() {
        return input;
    }

    public BufferedWriter getOutput() {
        return output;
    }

    public ClientThread(BufferedReader input, BufferedWriter output, GameServer gameServer) {
        this.input = input;
        this.output = output;
        this.gameServer = gameServer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = input.readLine();
                gameServer.sendScore(message, this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

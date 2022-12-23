package com.example.cardgame.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientThread implements Runnable {
    private final BufferedReader input;

    public BufferedReader getInput() {
        return input;
    }

    public BufferedWriter getOutput() {
        return output;
    }

    private final BufferedWriter output;
    private GameClient gameClient;

    public ClientThread(BufferedReader input, BufferedWriter output, GameClient gameClient) {
        this.input = input;
        this.output = output;
        this.gameClient = gameClient;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = input.readLine();
                gameClient.getApp().appendMessage(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.example.cardgame.client;

import com.example.cardgame.app.App;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class GameClient {

    private Socket socket;
    private ClientThread clientThread;

    public App getApp() {
        return app;
    }

    private final App app;

    public GameClient(App app) {
        this.app = app;
    }

    public void sendScore(String score) {
        try {
            clientThread.getOutput().write(score);
            clientThread.getOutput().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws IOException {
        String host = app.getUserConfig().getHost();
        int port = app.getUserConfig().getPort();

        socket = new Socket(host, port);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

        clientThread = new ClientThread(input, output, this);

        new Thread(clientThread).start();
    }
}

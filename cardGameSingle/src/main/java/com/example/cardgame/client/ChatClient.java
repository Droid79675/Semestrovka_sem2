package com.example.cardgame.client;

import com.example.cardgame.app.App;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClient {

    private Socket socket;
    private ClientThread clientThread;

    public App getChatApplication() {
        return chatApplication;
    }

    private final App chatApplication;

    public ChatClient(App chatApplication) {
        this.chatApplication = chatApplication;
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
        String host = chatApplication.getUserConfig().getHost();
        int port = chatApplication.getUserConfig().getPort();

        socket = new Socket(host, port);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

        clientThread = new ClientThread(input, output, this);

        new Thread(clientThread).start();
    }
}

package com.example.cardgame.model;

public class UserConfig {
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getScore() {
        return score;
    }

    public void setScore() {
        this.score++;
    }
    private String host;
    private int port;
    private int score;
    private int numberOfClients;

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients() {
        this.numberOfClients = numberOfClients;
    }
}

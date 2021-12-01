package com.example.uni_hub.game.logic;

public class GradingResult {
    private String clientID;
    private int points;

    public GradingResult(String clientID, int points) {
        this.clientID = clientID;
        this.points = points;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

package com.example.uni_hub.game.services;

public class GamePayload {
    private String method;
    private String clientId;
    private String clientName;
    private String clientEmail;
    private String gameId;
    private String gamePassword;
    private int playersNumber;

    public GamePayload(String method, String clientId, String clientName, String clientEmail, int playerNumber, String gamePassword) {
        this.method = method;
        this.clientId = clientId;
        this.clientEmail = clientEmail;
        this.playersNumber = playerNumber;
        this.gamePassword = gamePassword;
        this.clientName = clientName;
    }

    public GamePayload(String method, String clientId, String gameId) {
        this.method = method;
        this.clientId = clientId;
        this.gameId = gameId;
    }

    public GamePayload(String method, String clientId,String clientName, String gameId, String gamePassword) {
        this.method = method;
        this.clientId = clientId;
        this.gameId = gameId;
        this.gamePassword = gamePassword;
        this.clientName = clientName;
    }

    public GamePayload() {
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public String getGamePassword() {
        return gamePassword;
    }

    public void setGamePassword(String gamePassword) {
        this.gamePassword = gamePassword;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getPlayerNumber() {
        return playersNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playersNumber = playerNumber;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
}

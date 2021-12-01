package com.example.uni_hub.game.logic;

public class Player {
    private String playerID;
    private String clientName;
    private String playerPoints;
    private GameLogic answers;

    public Player() {
    }

    public Player(String playerID, String clientName, String playerPoints, GameLogic answers) {
        this.playerID = playerID;
        this.clientName = clientName;
        this.playerPoints = playerPoints;
        this.answers = answers;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(String playerPoints) {
        this.playerPoints = playerPoints;
    }

    public GameLogic getAnswers() {
        return answers;
    }

    public void setAnswers(GameLogic answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerID='" + playerID + '\'' +
                ", clientName='" + clientName + '\'' +
                ", playerPoints='" + playerPoints + '\'' +
                ", answers=" + answers +
                '}';
    }
}

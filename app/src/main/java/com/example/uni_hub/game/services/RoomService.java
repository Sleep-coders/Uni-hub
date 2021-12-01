package com.example.uni_hub.game.services;
import com.example.uni_hub.game.logic.Player;

import java.util.ArrayList;

public class RoomService {
    private String gameID;
    private int playerNumber;
    private String hostID;
    private String hostName;
    private String gamePassword;
    private ArrayList<String> roomMessages;
    private ArrayList<Player> players;

    public RoomService() {
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public String getGamePassword() {
        return gamePassword;
    }

    public void setGamePassword(String gamePassword) {
        this.gamePassword = gamePassword;
    }

    public ArrayList<String> getRoomMessages() {
        return roomMessages;
    }

    public void setRoomMessages(ArrayList<String> roomMessages) {
        this.roomMessages = roomMessages;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "RoomService{" +
                "gameID='" + gameID + '\'' +
                ", playerNumber=" + playerNumber +
                ", hostID='" + hostID + '\'' +
                ", hostName='" + hostName + '\'' +
                ", gamePassword='" + gamePassword + '\'' +
                ", roomMessages=" + roomMessages +
                ", players=" + players +
                '}';
    }
}

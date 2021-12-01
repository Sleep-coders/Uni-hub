package com.example.uni_hub.game.logic;

import java.util.List;

public class GameLogic {
    private String method;
    private String gameID;
    private String clientID;
    private String human;
    private String animal;
    private String plant;
    private String country;
    private String thing;
    private List<GradingResult> gradingResults;

    public GameLogic(String method, String gameID, String clientID, String human, String animal, String plant, String country, String thing) {
        this.human = human;
        this.animal = animal;
        this.plant = plant;
        this.country = country;
        this.thing = thing;
        this.method = method;
        this.gameID = gameID;
        this.clientID = clientID;
    }

    public GameLogic(String human, String animal, String plant, String country, String thing) {
        this.human = human;
        this.animal = animal;
        this.plant = plant;
        this.country = country;
        this.thing = thing;
    }

    public GameLogic(String method, String gameID, List<GradingResult> gradingResults) {
        this.method = method;
        this.gradingResults = gradingResults;
        this.gameID = gameID;
    }



    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getHuman() {
        return human;
    }

    public void setHuman(String human) {
        this.human = human;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    @Override
    public String toString() {
        return "GameLogic{" +
                "method='" + method + '\'' +
                ", gameID='" + gameID + '\'' +
                ", clientID='" + clientID + '\'' +
                ", human='" + human + '\'' +
                ", animal='" + animal + '\'' +
                ", plant='" + plant + '\'' +
                ", country='" + country + '\'' +
                ", thing='" + thing + '\'' +
                '}';
    }
}

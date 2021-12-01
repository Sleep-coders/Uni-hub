package com.example.uni_hub.game.services;

import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AppUser;
import com.example.uni_hub.game.logic.GameLogic;
import com.example.uni_hub.game.logic.GradingResult;
import com.example.uni_hub.game.ui.CreateNewRoom;
import com.example.uni_hub.game.ui.JoinORCreate;
import com.example.uni_hub.game.ui.host.HostGameGrading;
import com.example.uni_hub.game.ui.host.HostGameRoom;
import com.example.uni_hub.game.ui.host.HostInRoomGame;
import com.example.uni_hub.game.ui.player.PlayerGameGrading;
import com.example.uni_hub.game.ui.player.PlayerGameRoom;
import com.example.uni_hub.game.ui.player.PlayerInRoomGame;
import com.example.uni_hub.game.ui.player.PlayerJoinPassword;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.crossbar.autobahn.websocket.WebSocketConnection;
import io.crossbar.autobahn.websocket.WebSocketConnectionHandler;
import io.crossbar.autobahn.websocket.exceptions.WebSocketException;
import io.crossbar.autobahn.websocket.types.ConnectionResponse;

public class ConnectionManger {

    private static final String TAG = ConnectionManger.class.getName();
    public static WebSocketConnection connection;
    public static String clientID;
    public static RoomService room;
    public static String randomChar;
    private static Gson gson = new Gson();
    private static String userNickName;


    public ConnectionManger() {
    }

    private void socketConnect() throws WebSocketException {

        connection = new WebSocketConnection();
        getUserName();

        connection.connect("ws://b160-212-34-22-190.ngrok.io", new WebSocketConnectionHandler() {

            @Override
            public void onConnect(ConnectionResponse response) {
                Log.i(TAG, "Connected to server");

            }

            @Override
            public void onOpen() {

            }

            @Override
            public void onClose(int code, String reason) {
                Log.i(TAG, "Connection closed");
            }

            @Override
            public void onMessage(String payload) {
                try {
                    JSONObject jsonObject = new JSONObject(payload);
                    Log.i(TAG, "onMessage: =====>" + jsonObject.get("method"));

                    if (jsonObject.get("method").toString().equals("connect")) {

                        clientID = jsonObject.get("clientId").toString();


                    } else if (jsonObject.get("method").toString().equals("create")) {

                        String gameInfo = jsonObject.get("game").toString();

                        room = gson.fromJson(gameInfo, RoomService.class);
                        CreateNewRoom.createNewGameThread.run();
                        Log.i(TAG, "onMessage: GAME INFO ====>" + room.toString());

                    } else if (jsonObject.get("method").toString().equals("verify")) {

                        String gameInfo = jsonObject.get("check").toString();
                        if (gameInfo.equals("true")) {
                            Log.i(TAG, "onMessage: GAME INFO ====>" + gameInfo);
                            JoinORCreate.startPassWordActivityThread.run();
                        } else if (gameInfo.equals("false")) {
                            Log.i(TAG, "onMessage: GAME INFO ====>" + gameInfo);
                            JoinORCreate.changeVisibilityThread.run();
                        }


                    } else if (jsonObject.get("method").toString().equals("startGameForAll")) {
                        randomChar = jsonObject.get("randomChar").toString();
                        Log.i(TAG, "onMessage: RANDOM CHARACTER IS ====> " + randomChar);
                        if (clientID.equals(jsonObject.get("hostID").toString())) {
                            HostGameRoom.startNewGameThread.run();
                        } else {
                            PlayerGameRoom.startNewGameThread.run();
                        }

                    } else if (jsonObject.get("method").toString().equals("sendGameLogicToServer")) {

                        String gameInfoUpdate = jsonObject.get("game").toString();
                        room = gson.fromJson(gameInfoUpdate, RoomService.class);

                        if (clientID.equals(jsonObject.get("hostID").toString())) {
                            HostInRoomGame.sendGameDataThread.run();
                        } else {
                            PlayerInRoomGame.sendGameDataThread.run();
                        }

                        Log.i(TAG, "RESULTS: =====> " + room);

                    } else if (jsonObject.get("method").toString().equals("addPointsToTheUsers")) {

                        String gameInfoUpdate = jsonObject.get("game").toString();
                        room = gson.fromJson(gameInfoUpdate, RoomService.class);
                        if (clientID.equals(jsonObject.get("hostID").toString())) {
                            HostGameGrading.startResultsThread.run();
                        } else {
                            PlayerGameGrading.startResultsThread.run();
                        }
                        Log.i(TAG, "RESULTS: =====> " + room);

                    } else if (jsonObject.get("method").toString().equals("join")) {

                        String passCheck = jsonObject.get("passCheck").toString();
                        switch (passCheck) {
                            case "false":
                                Log.i(TAG, "onMessage: ====> Game Password is wrong");
                                PlayerJoinPassword.changeVisibilityThread.run();
                                break;
                            case "trueButFalse":
                                Log.i(TAG, "onMessage: =====> Either reached max players or the game already started");
                                PlayerJoinPassword.changeVisibilityThread2.run();
                                break;
                            case "true":
                                String gameInfoUpdate = jsonObject.get("game").toString();
                                room = gson.fromJson(gameInfoUpdate, RoomService.class);
                                PlayerJoinPassword.startGameRoomThread.start();
                                Log.i(TAG, "onMessage: =====> Join the game for all boys");
                                break;
                        }

                    } else if (jsonObject.get("method").toString().equals("update")) {

                        String gameInfoUpdate = jsonObject.get("game").toString();
                        room = gson.fromJson(gameInfoUpdate, RoomService.class);
                        if (clientID.equals(jsonObject.get("hostID").toString())) {
                            Log.i(TAG, "OnHostEqualClient ==================>: " + room.getHostName());
                            HostGameRoom.playerJoinedThread.run();
                        } else {
                            Log.i(TAG, "OnHostNotEqualClient ==================>: " + room.getHostName());
                            PlayerGameRoom.playerJoinedThread.run();
                        }

                    } else if (jsonObject.get("method").toString().equals("endGameForAll")) {

                        if (clientID.equals(jsonObject.get("hostID").toString())) {
                            HostGameRoom.endGameForAllThread.run();
                        } else {
                            PlayerGameRoom.endGameForAllThread.run();
                        }

                        Log.i(TAG, "onMessage: GameEndedForAll ====>" + room.toString());

                    } else if (jsonObject.get("method").toString().equals("endGameForOne")) {

                        String gameInfoUpdate = jsonObject.get("game").toString();
                        room = gson.fromJson(gameInfoUpdate, RoomService.class);

                        if (clientID.equals(jsonObject.get("clientID").toString())) {
                            PlayerGameRoom.endGameForOneThread.run();
                        } else {
                            if (clientID.equals(jsonObject.get("hostID").toString())) {
                                HostGameRoom.playerJoinedThread.run();
                            } else {
                                PlayerGameRoom.playerJoinedThread.run();
                            }

                        }
                        Log.i(TAG, "onMessage: GameEndedForAll ====>" + room.toString());

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void connectToServer() {

        try {
            socketConnect();
        } catch (WebSocketException exception) {
            Log.i(TAG, "socketError ===> : " + exception.getMessage());
        }

    }


    // Verifying GameID
    public static void verifyGameId(String roomIDString) {
        GamePayload verifyGameIDPayload = new GamePayload("verify", clientID, roomIDString);
        connection.sendMessage(gson.toJson(verifyGameIDPayload));

    }

    // Join game as a player
    public static void joinRoom(String roomIdString, String roomPasswordString) {
        GamePayload verifyGamePasswordPayload = new GamePayload("join", clientID, userNickName, roomIdString, roomPasswordString);
        connection.sendMessage(gson.toJson(verifyGamePasswordPayload));

    }


    //   Creating the Game
    public static void createNewGame(String password, String email, int playerNumber) {
        GamePayload gameCreate = new GamePayload("create", clientID, userNickName, email, playerNumber, password);
        String createNewGamePayLoad = gson.toJson(gameCreate);
        connection.sendMessage(createNewGamePayLoad);
        Log.i(TAG, "createGame: ============>" + createNewGamePayLoad);
    }

    // Host Start Game
    public static void startGameForAll() {
        GamePayload gameStart = new GamePayload("startGameForAll", clientID, room.getGameID());
        String startGamePayLoad = gson.toJson(gameStart);
        connection.sendMessage(startGamePayLoad);
        Log.i(TAG, "startGameForAll: ============>" + startGamePayLoad);
    }

    // Send game data to server
    public static void sendGameLogicToServer(String human, String animal, String plant, String country, String thing) {
        GameLogic gameLogic = new GameLogic("sendGameLogicToServer", room.getGameID(), clientID, human, animal, plant, country, thing);
        String jsonGameLogic = gson.toJson(gameLogic);
        connection.sendMessage(jsonGameLogic);
        Log.i(TAG, "onTimerFinish: ===> " + gameLogic.toString());
    }

    // Add points to users
    public static void addPointsToUsers(List<GradingResult> gradingResultsList) {
        GameLogic gameLogic = new GameLogic("addPointsToTheUsers", room.getGameID(), gradingResultsList);
        String usersPoints = gson.toJson(gameLogic);
        connection.sendMessage(usersPoints);
    }

    // End Game For All
    public static void endGameForAll() {
        GamePayload gameEnd = new GamePayload("endGameForAll", clientID, room.getGameID());
        String gameEndingPayload = gson.toJson(gameEnd);
        connection.sendMessage(gameEndingPayload);
    }

    // End Game For One Player
    public static void endGameForOne() {
        GamePayload gameEnd = new GamePayload("endGameForOne", clientID, room.getGameID());
        String gameEndingPayload = gson.toJson(gameEnd);
        connection.sendMessage(gameEndingPayload);
    }


    // get User name from Cognito
    public void getUserName() {
        String email = Amplify.Auth.getCurrentUser().getUsername();
        Amplify.API.query(ModelQuery.list(AppUser.class, AppUser.USER_EMAIL.eq(email)),
                success -> {
                    for (AppUser user : success.getData().getItems()) {
                        userNickName = user.getUserNickname();
                        break;
                    }
                },
                error -> {
                    Log.i("getUserID", "Error in getting user id");
                });
    }
}
